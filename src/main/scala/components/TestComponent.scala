package components

import models.request.test.TestRequest
import models.response.test.TestResponse
import persistance.TestQuery
import zio.{Task, ZIO, ZLayer}

trait TestComponent {
  def test(testRequest: TestRequest): Task[TestResponse]
}

object TestComponent {
  val live = ZLayer {
    for {
      query <- ZIO.service[TestQuery]
    } yield new TestComponentLive(
        query
    )
  }
}