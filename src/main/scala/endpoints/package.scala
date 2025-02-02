import models.response.error.ServiceError
import sttp.model.StatusCode
import sttp.tapir.Schema
import sttp.tapir.json.circe.jsonBody
import sttp.tapir.ztapir._

package object endpoints {

  implicit lazy val serviceErrorSchema: Schema[ServiceError] = Schema.derived[ServiceError]

  private[endpoints] lazy val baseEndpoint =
    endpoint
      .errorOut(errorOut)

  private[endpoints] lazy val errorOut = oneOf[ServiceError](badRequest, internalServiceError)

  private val badRequest = oneOfVariant(
    StatusCode.BadRequest,
    jsonBody[ServiceError].description("Неверные параметры запроса")
  )

  private val internalServiceError = oneOfVariant(
    StatusCode.InternalServerError,
    jsonBody[ServiceError].description("Внутренняя ошибка сервиса")
  )
}
