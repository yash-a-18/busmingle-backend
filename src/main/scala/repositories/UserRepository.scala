package busmingle.repositories

import zio.*
import io.getquill.* 
import io.getquill.jdbczio.Quill

import busmingle.model.User

trait UserRepository {
  def findByFirebaseUid(uid: String): Task[Option[User]]
}

class UserRepositoryImpl(quill: Quill.Postgres[SnakeCase]) extends UserRepository:
    import quill.* //gives us access to methods such as run, query, filter or lift

    inline given schema: SchemaMeta[User] = schemaMeta[User]("users") // Table name `"users"`
    inline given insMeta: InsertMeta[User] = insertMeta[User](_.id) // Columns to generate on its own
    inline given upMeta: UpdateMeta[User] = updateMeta[User](_.id)

    override def findByFirebaseUid(uid: String): Task[Option[User]] = {
        run{
            query[User]
                .filter(_.firebaseUid == lift(uid))
        }.map(_.headOption)
    }

object UserRepositoryImpl:
    val layer = ZLayer {
        ZIO.service[Quill.Postgres[SnakeCase]].map(quill => UserRepositoryImpl(quill))
    }