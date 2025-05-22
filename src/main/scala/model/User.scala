package busmingle.model

import io.getquill.*
import zio.json.*
import java.util.UUID
import java.time.Instant

case class User(
  id: UUID,
  firebaseUid: String,
  name: Option[String],
  photoUrl: Option[String],
  bio: Option[String],
  createdAt: Instant
)

object User {
  given JsonCodec[User] = DeriveJsonCodec.gen[User]
}
