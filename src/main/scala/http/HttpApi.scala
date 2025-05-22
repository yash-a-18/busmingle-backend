package busmingle.http

import busmingle.http.controllers.*

object HttpApi:
    def gatherRoutes(controllers: List[BaseController]) =
        controllers.flatMap(_.routes)

    def makeControllers = for {
        healthCheck <- HealthController.makeZIO
        // Keep adding controllers here
    } yield List(healthCheck)

    val endpointsZIO = makeControllers.map(gatherRoutes)