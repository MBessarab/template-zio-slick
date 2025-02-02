package persistance.interop

import slick.dbio.DBIO
import slick.jdbc.JdbcBackend
import zio.{RIO, Task, ZIO}

object syntax {
  implicit class SlickZioOps(private val zio: ZIO.type) extends AnyVal {

    def fromPureDBIO[A](dbio: => DBIO[A]): RIO[JdbcBackend#Database, A] =
      for {
        db <- ZIO.service[JdbcBackend#Database]
        result <- fromPureDBIO(dbio, db)
      } yield result

    def fromPureDBIO[A](action: => DBIO[A], db: JdbcBackend#Database): Task[A] =
      ZIO.fromFuture(_ => db.run(action))
  }
}
