package endpoints.v1

import models.request.test.TestRequest
import models.response.test.TestResponse
import sttp.tapir.Schema
import sttp.tapir.generic.Configuration
import sttp.tapir.json.circe.jsonBody

object TestEndpoints {

  implicit lazy val snakeCaseConfSchema: Configuration = Configuration.default.withSnakeCaseMemberNames

  implicit lazy val testRequestSchema: Schema[TestRequest] = Schema.derived
  implicit lazy val testResponseSchema: Schema[TestResponse] = Schema.derived

  private val baseTestEndpoint = baseEndpointV1.in("test")

  def testEndpoint = baseTestEndpoint
    .in("test")
    .post
    .in(jsonBody[TestRequest])
    .out(jsonBody[TestResponse])
}
