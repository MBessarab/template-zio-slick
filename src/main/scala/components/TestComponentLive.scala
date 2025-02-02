package components

import models.request.test.TestRequest
import models.response.test.TestResponse
import persistance.TestQuery
import zio.Task

class TestComponentLive(query: TestQuery) extends TestComponent {
  override def test(testRequest: TestRequest): Task[TestResponse] = for {
    _ <- query.testSelect
  } yield TestResponse(s"test response with req id: ${testRequest.requestId}")
}
