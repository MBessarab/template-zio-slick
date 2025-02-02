package persistance

import zio.{Task, ZIO, ZLayer}

trait TestQuery {
  def testSelect: Task[Unit]
}


object TestQuery {
  val live: ZLayer[SlickPostgresqlProvider, Nothing, TestQueryLive] = ZLayer {
    for {
      dbProvider <- ZIO.service[SlickPostgresqlProvider]
      jdbcProfile <- dbProvider.jdbcProfile
    } yield new TestQueryLive(dbProvider, jdbcProfile)
  }
}