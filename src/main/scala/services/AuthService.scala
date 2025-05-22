package busmingle.services

import zio.*
import sttp.client4.*
import sttp.client4.httpclient.zio.HttpClientZioBackend
import sttp.client4.ziojson.*
import zio.json.*
import busmingle.model.User

final case class FirebaseUserInfo(
  user_id: String,
  name: Option[String],
  email: Option[String],
  picture: Option[String]
)

object FirebaseUserInfo {
  given JsonDecoder[FirebaseUserInfo] = DeriveJsonDecoder.gen[FirebaseUserInfo]
}

trait AuthService {
  def verifyIdToken(idToken: String): Task[FirebaseUserInfo]
}

// Updated to use Backend[Task]
class AuthServiceImpl(client: Backend[Task], firebaseProjectId: String)
    extends AuthService {

  override def verifyIdToken(idToken: String): Task[FirebaseUserInfo] = {
    val uri = uri"https://oauth2.googleapis.com/tokeninfo?id_token=$idToken"

    basicRequest
      .get(uri)
      .response(asJson[FirebaseUserInfo])
      .send(client)
      .flatMap { response =>
        response.body match {
          case Right(info) => ZIO.succeed(info)
          case Left(err)   => ZIO.logError(s"Invalid Firebase token: $err") *> ZIO.fail(new RuntimeException(s"Invalid Firebase token: $err"))
        }
      }
  }
}

object AuthServiceImpl {
  def live(firebaseProjectId: String): ZLayer[Any, Throwable, AuthService] =
    ZLayer.scoped {
      for {
        backend <- HttpClientZioBackend.scoped()
      } yield AuthServiceImpl(backend, firebaseProjectId)
    }

  def apply(client: Backend[Task], firebaseProjectId: String): AuthService =
    new AuthServiceImpl(client, firebaseProjectId)
}
