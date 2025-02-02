import components.TestComponent
import config.AppConfig
import persistance.{SlickPostgresqlProvider, TestQuery}
import services.ApiService
import slick.jdbc.JdbcProfile
import zio.config.getConfig
import zio.http._
import zio.{Scope, ZIO, ZIOAppArgs, ZIOAppDefault, ZLayer}

object MainApp extends ZIOAppDefault {

  private val server = for {
    appConf <- getConfig[AppConfig]
    routes <- ApiService.routes
    _ <- Server
      .install(routes)
      .flatMap(port => ZIO.logInfo(s"Server started on $port port."))
      .zipRight(ZIO.never)
      .provide(Server.defaultWith(_.binding(appConf.http.host, appConf.http.port)))
  } yield ()

  override def run: ZIO[ZIOAppArgs with Scope, Any, Any] = server.provide(
    ApiService.live,
    AppConfig.appConfigLayer,

    TestComponent.live,

    ZLayer.succeed[JdbcProfile](slick.jdbc.PostgresProfile),
    SlickPostgresqlProvider.live,
    TestQuery.live,
  )
}