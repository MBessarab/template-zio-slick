package models.response.error

import io.circe.Codec
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class ServiceError(
                       message: String
                       )

trait ServiceErrorCodec {
  implicit val conf: Configuration = Configuration.default.withSnakeCaseMemberNames
  implicit lazy val codec: Codec[ServiceError] = deriveConfiguredCodec[ServiceError]
}

object ServiceError extends ServiceErrorCodec