package busmingle

import sttp.tapir.server.ziohttp.*
import sttp.tapir.json.zio.*
import zio.*

import zio.http.Middleware.CorsConfig
import zio.http.Server
import zio.http.Header.{AccessControlAllowMethods, AccessControlAllowOrigin, Origin}
import zio.http.Method
import zio.http.Header.AccessControlAllowHeaders
import zio.http.Middleware
import busmingle.model.Health
import busmingle.http.HttpApi
import busmingle.services.HealthServiceImpl
import zio.http.Server.RequestStreaming

object Main extends ZIOAppDefault {
  private val corsConfig: CorsConfig = CorsConfig(
    allowedOrigin = _ => Some(AccessControlAllowOrigin.All),
    allowedMethods = AccessControlAllowMethods(
      Method.GET,
      Method.POST,
      Method.PUT,
      Method.DELETE,
      Method.OPTIONS  // Include OPTIONS for preflight requests
    ),
    allowedHeaders = AccessControlAllowHeaders.All
  )

  val customConfig = Server.Config.default.copy(
    requestStreaming = RequestStreaming.Enabled // increase request body length
  )

  val serverProgram = for {
    endpoints <- HttpApi.endpointsZIO
    corsMiddleware = Middleware.cors(corsConfig)
    server <- Server.serve(
      corsMiddleware(
        ZioHttpInterpreter(
          ZioHttpServerOptions.default
        ).toHttp(endpoints)
      )
    )
  } yield ()

  override def run =
    ZIO.logInfo("Starting BusMingle backend...")
    serverProgram.provide(
      Server.live,
      ZLayer.succeed(customConfig),
      HealthServiceImpl.layer
    )
}