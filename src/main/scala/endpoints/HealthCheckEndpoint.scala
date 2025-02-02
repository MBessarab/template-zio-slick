package endpoints

import sttp.model.StatusCode
import sttp.tapir.{endpoint, statusCode, stringJsonBody}

object HealthCheckEndpoint {
  def getHealth = endpoint
    .in("api")
    .in("health")
    .out(stringJsonBody.and(statusCode(StatusCode.Ok)))
}
