package services

import components.TestComponent
import zio.{Task, ZIO, ZLayer}
import zio.http.{Response, Routes}


trait ApiService {
  def routes: Task[Routes[Any, Response]]
}
object ApiService {

  val live = ZLayer {
    for {
      testComponent <- ZIO.service[TestComponent]
    } yield new ApiServiceLive(
      testComponent
    )
  }

  def routes = ZIO.serviceWithZIO[ApiService](_.routes)

}
