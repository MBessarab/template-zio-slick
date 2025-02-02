package models.response.test

import io.circe.Codec
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class TestResponse(message: String)

trait TestResponseCodec {
  implicit val conf: Configuration = Configuration.default.withSnakeCaseMemberNames
  implicit lazy val codec: Codec[TestResponse] = deriveConfiguredCodec[TestResponse]
}

object TestResponse extends TestResponseCodec