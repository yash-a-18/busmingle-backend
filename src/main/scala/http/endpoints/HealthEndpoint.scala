package busmingle.http.endpoints

import sttp.tapir.*
import sttp.tapir.json.zio.*
import busmingle.model.Health
import sttp.tapir.generic.auto.*

trait HealthEndpoint extends BaseEndpoint:
    // Define the server logic for the health endpoint
    val healthEndpoint = baseEndpoint
        .get
        .in("health")
        .out(jsonBody[Health])