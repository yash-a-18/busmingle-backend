package busmingle.services

import zio.*
import busmingle.model.Health

//Logic
// in between the HTTP layer and the Database layer
trait HealthService:
    def healthCheck(): Task[Health]

class HealthServiceImpl extends HealthService:
    override def healthCheck(): Task[Health] = ZIO.succeed(Health("OK"))
    
object HealthServiceImpl:
    val layer = ZLayer{
        for {
            _ <- ZIO.logInfo("Creating HealthService")
        } yield new HealthServiceImpl()
    }