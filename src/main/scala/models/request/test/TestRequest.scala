package models.request.test

import io.circe.Codec
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec


case class TestRequest(
                      requestId: String
                      )

trait TestRequestCodec {
  implicit val conf: Configuration = Configuration.default.withSnakeCaseMemberNames
  implicit lazy val codec: Codec[TestRequest] = deriveConfiguredCodec[TestRequest]
}

object TestRequest extends TestRequestCodec