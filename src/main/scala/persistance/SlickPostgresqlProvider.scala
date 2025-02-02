package persistance

import slick.jdbc.{JdbcBackend, JdbcProfile}
import zio.{UIO, ZIO, ZLayer}


trait SlickPostgresqlProvider {
  def db: UIO[JdbcBackend#Database]
  def jdbcProfile: UIO[JdbcProfile]
}

object SlickPostgresqlProvider {
  val live: ZLayer[JdbcProfile, Throwable, SlickPostgresqlProvider] = ZLayer.scoped {
    for {
      profile <- ZIO.service[JdbcProfile]
      db = ZIO.attempt(profile.backend.Database.forConfig("postgres"))
      managedDb <- ZIO.acquireRelease(db)(db => ZIO.succeed(db.close()))
    } yield new SlickPostgresqlProvider {
      override def jdbcProfile: UIO[JdbcProfile] = ZIO.succeed(profile)

      override def db: UIO[JdbcBackend#JdbcDatabaseDef] = ZIO.succeed(managedDb)
    }
  }
}