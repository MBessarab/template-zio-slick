package services

import components.TestComponent
import endpoints.HealthCheckEndpoint
import endpoints.v1.TestEndpoints
import models.response.error.ServiceError
import services.middleware.corsSupport
import sttp.tapir.server.ziohttp.{ZioHttpInterpreter, ZioHttpServerOptions}
import sttp.tapir.ztapir.RichZEndpoint
import zio.{Task, ZIO}
import zio.http._
import zio._


class ApiServiceLive(
                      testComponent: TestComponent
                    ) extends ApiService {
  override def routes: Task[Routes[Any, Response]] = ZIO.succeed(httpApp)

  def httpApp =
    zRoutes @@
      corsSupport @@
      Middleware.debug

  val zServerOptions: ZioHttpServerOptions[Any] =
    ZioHttpServerOptions
      .default

  def zRoutes = ZioHttpInterpreter(zServerOptions).toHttp(
    testHttp ++
      healthHttp
  )

  def testHttp = List(
    TestEndpoints
      .testEndpoint
      .zServerLogic(testRequest =>
        testComponent.test(testRequest)
          .mapBoth(err => ServiceError(err.toString), identity)
      )
  )

  def healthHttp = List(
    HealthCheckEndpoint
      .getHealth
      .zServerLogic(_ => ZIO.succeed(""))
  )
}
