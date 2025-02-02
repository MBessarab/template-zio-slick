package endpoints

package object v1 {
  private[v1] lazy val baseEndpointV1 =
    baseEndpoint
      .in("api")
      .in("v1")
}
