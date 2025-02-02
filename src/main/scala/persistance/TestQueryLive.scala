package persistance
import slick.jdbc.JdbcProfile
import zio.{Task, ZIO, ZLayer}
import persistance.interop.syntax._

class TestQueryLive(
                       dbProvider: SlickPostgresqlProvider,
                       jdbcProfile: JdbcProfile,
                       ) extends TestQuery {

  import jdbcProfile.api._

  override def testSelect: Task[Unit] = {
//    implicit val getResult: GetResult[TestResponseDB] = GetResult[TestResponseDB](rs =>
//      TestResponseDB(
//        field1 = rs.<<,
//        field2 = rs.<<
//      )
//    )

    for {
      sql <- ZIO.attempt("Select 1")
      result <- ZIO.fromPureDBIO(sql"#$sql".as[Unit]).provide(ZLayer.fromZIO(dbProvider.db))
    } yield ()
  }
}
