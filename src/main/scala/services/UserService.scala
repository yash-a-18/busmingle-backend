package busmingle.services

import zio.*
import io.getquill.*
import java.util.UUID

import busmingle.model.User
import busmingle.repositories.UserRepository

trait UserService {
  def findByFirebaseUid(uid: String): Task[Option[User]]
}

class UserServiceImpl private (repo: UserRepository) extends UserService {

  override def findByFirebaseUid(uid: String): Task[Option[User]] = {
    repo.findByFirebaseUid(uid).map(_.headOption)
  }
}

object UserServiceImpl:
    val layer = ZLayer{
        for{
            repo <- ZIO.service[UserRepository]
        } yield new UserServiceImpl(repo)
    }