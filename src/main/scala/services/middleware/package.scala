package services

import zio.http.Header.{AccessControlAllowOrigin, AccessControlAllowMethods}
import zio.http.Middleware.{cors, CorsConfig}
import zio.http._

package object middleware {
  val corsSupport: Middleware[Any] = cors(config =
  CorsConfig(
    allowedOrigin = _ => Some(AccessControlAllowOrigin.All),
    allowedMethods = AccessControlAllowMethods(Method.POST, Method.GET, Method.DELETE, Method.PUT)
  )
  )
}
