package busmingle.http.controllers

import zio.*
import busmingle.services.HealthService
import busmingle.http.endpoints.HealthEndpoint
import sttp.tapir.server.ServerEndpoint
import sttp.model.StatusCode

class HealthController private (service: HealthService) extends BaseController with HealthEndpoint:
  val health: ServerEndpoint[Any, Task] = healthEndpoint.serverLogic { _ =>
    service.healthCheck().mapError { throwable =>
      // Map Throwable to (StatusCode, String)
      (StatusCode.InternalServerError, throwable.getMessage)
    }.either
  }

  override val routes: List[ServerEndpoint[Any, Task]] = List(health)

object HealthController:
  val makeZIO = for {
    service <- ZIO.service[HealthService]
  } yield new HealthController(service)