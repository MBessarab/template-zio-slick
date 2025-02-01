ThisBuild / scalaVersion := "2.13.16"

lazy val root = (project in file("."))
  .enablePlugins(JavaServerAppPackaging)
  .settings(
    name := "template-zio-slick",
    version := "0.0.1",
    libraryDependencies ++= Dependencies.service,
    mainClass := Some("")
  )

ThisBuild / libraryDependencySchemes += "io.circe" %% "circe-core" % VersionScheme.Always
