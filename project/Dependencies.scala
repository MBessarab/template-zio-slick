import sbt._

import scala.language.postfixOps

object Dependencies {

  lazy val service = zio.all ++
    kafka.all ++
    tapir.all ++
    circe.all ++
//    derevo.all ++
    java.all ++
    netty.all ++
    slick.all ++
    misc.all

  object zio {
    lazy val all = zio ++ configZio ++ loggingZio ++ Seq(http)

    lazy val configZio = Seq(config, configTs, configMagnolia)
    lazy val loggingZio = Seq(logging, loggingSlf4j)

    lazy val zio = Seq(
      base,
      macros,
      stream,
      streamInterop,
      concurrency
    )

    private[zio] lazy val base = "dev.zio" %% "zio" % version
    def macros = "dev.zio" %% "zio-macros" % version

    private[zio] lazy val stream = "dev.zio" %% "zio-streams" % version
    private[zio] lazy val concurrency = "dev.zio" %% "zio-concurrent" % version
    private[zio] lazy val streamInterop = "dev.zio" %% "zio-interop-reactivestreams" % streamInteropVersion
    private[zio] lazy val config = "dev.zio" %% "zio-config" % configVersion
    private[zio] lazy val configTs = "dev.zio" %% "zio-config-typesafe" % configVersion
    private[zio] lazy val configMagnolia = "dev.zio" %% "zio-config-magnolia" % configVersion
    private[zio] lazy val logging = "dev.zio" %% "zio-logging" % loggingVersion
    private[zio] lazy val loggingSlf4j = "dev.zio" %% "zio-logging-slf4j" % loggingVersion
    private[zio] lazy val http = ("dev.zio" %% "zio-http" % zioHttpVersion).excludeAll(ExclusionRule("io.netty"))


    private[zio] lazy val version ="2.0.19"
    private[zio] lazy val zioHttpVersion ="3.0.0"
    private[zio] lazy val streamInteropVersion ="2.0.2"
    private[zio] lazy val configVersion ="3.0.7"
    private[zio] lazy val loggingVersion ="2.1.13"
  }

  object kafka {
    lazy val all = Seq(zioKafka, kafkaClient)

    private[kafka] lazy val zioKafka = ("dev.zio" %% "zio-kafka" % zioKafkaVersion)
      .exclude("org.apache.kafka", "kafka-clients")
    private[kafka] lazy val kafkaClient = "org.apache.kafka" % "kafka-clients" % kafkaClientVersion

    private[kafka] lazy val zioKafkaVersion = "2.2"
    private[kafka] lazy val kafkaClientVersion = "2.2.0"
  }

  object tapir {
    lazy val all = Seq(
      tapir,
      tapirSwagger,
      tapirZio,
      tapirCirce
    )

    private[tapir] lazy val tapir = "com.softwaremill.sttp.tapir" %% "tapir-core" % version
    private[tapir] lazy val tapirSwagger = ("com.softwaremill.sttp.tapir" %% "tapir-swagger-ui-bundle" % version)
      .excludeAll(ExclusionRule("io.circe"))
    private[tapir] lazy val tapirZio = ("com.softwaremill.sttp.tapir" %% "tapir-zio-http-server" % version)
    private[tapir] lazy val tapirCirce = ("com.softwaremill.sttp.tapir" %% "tapir-json-circe" % version)
      .excludeAll(ExclusionRule("io.circe"))

    private[tapir] lazy val version = "1.11.2"
  }

  object circe {
    lazy val all = Seq(
      core,
      generic,
      extras,
      parser,
      parserYaml,
      refined
    )

    private[circe] lazy val core = "io.circe" %% "circe-core" % version
    private[circe] lazy val generic = "io.circe" %% "circe-generic" % version
    private[circe] lazy val extras = "io.circe" %% "circe-generic-extras" % version
    private[circe] lazy val parser = "io.circe" %% "circe-parser" % version
    private[circe] lazy val parserYaml = "io.circe" %% "circe-yaml" % yamlVersion

    private def refined = "io.circe" %% "circe-refined" % refinedVersion

    private[circe] lazy val version = "0.14.4"
    private[circe] lazy val yamlVersion = "0.16.0"
    private def refinedVersion = "0.14.1"
  }

  object derevo {
    def all = xs.map("tf.tofu" %% _ % version)

    private def xs = Seq(
      "derevo-circe-magnolia"
    )
    private def version = "0.13.0"
  }

  object java {
    lazy val all = Seq(
      apacheCommons,
      logback,
      postgres,
      cronUtils
    )

    private[java] lazy val apacheCommons = "commons-io" % "commons-io" % apacheCommonsVersion
    private[java] lazy val logback = "ch.qos.logback" % "logback-classic" % logbackVersion

    private[java] lazy val postgres = "org.postgresql" % "postgresql" % postgresVersion
//    private[java] lazy val liquibase = "org.liquibase" % "liquibase-core" % "4.8.0"
    private[java] lazy val cronUtils = "com.cronutils" % "cron-utils" % cronUtilsVersion

    private[java] lazy val apacheCommonsVersion = "2.15.0"
    private[java] lazy val logbackVersion = "1.5.6"
    private[java] lazy val postgresVersion = "42.7.3"
    private[java] lazy val cronUtilsVersion = "9.2.0"
  }

  object netty {
    def all = xs.map("io.netty" % _ % version) ++ Seq(
      ("io.netty" % "netty-transport-native-epoll" % version % Runtime).classifier("linux-x86_64"),
//      ("io.netty" % "netty-transport-native-epoll" % version % Runtime).classifier("linux-aarch_64"),
      ("io.netty" % "netty-transport-native-kqueue" % version % Runtime).classifier("osx-x86_64"),
//      ("io.netty" % "netty-transport-native-kqueue" % version % Runtime).classifier("linux-aarch_64"),
    )

    private[netty] def xs = Seq(
      "netty-codec-http",
      "netty-handler-proxy",
      "netty-transport-native-epoll",
      "netty-transport-native-kqueue"
    )

    private[netty] lazy val version = "4.1.117.Final"
  }

  object slick {
    lazy val all = Seq(
      slick,
      slickHikari,
      slickPg
    )

    private[slick] lazy val slick = "com.typesafe.slick" %% "slick" % slickVersion
    private[slick] lazy val slickHikari = "com.typesafe.slick" %% "slick-hikaricp" % slickVersion
    private[slick] lazy val slickPg = "com.github.tminglei" %% "slick-pg" % slickPgVersion

    private[slick] lazy val slickVersion = "3.5.1"
    private[slick] lazy val slickPgVersion = "0.22.1"
  }

  object misc {
    lazy val all = Seq(
      jwtCirce,
      sttpClient,
      sttpClientJson,
      sttpClientZio
    )

    private[misc] lazy val jwtCirce = ("com.github.jwt-scala" %% "jwt-circe" % jwtVersion)
      .excludeAll(ExclusionRule("io.circe"))

    private[misc] lazy val sttpClient = "com.softwaremill.sttp.client3" %% "core" % sttpClientVersion
    private[misc] lazy val sttpClientJson = "com.softwaremill.sttp.client3" %% "json-common" % sttpClientVersion
    private[misc] lazy val sttpClientZio = "com.softwaremill.sttp.client3" %% "zio" % sttpClientVersion

    private[misc] lazy val jwtVersion = "9.4.0"
    private[misc] lazy val sttpClientVersion = "3.8.16"
  }
}
