package config

import com.typesafe.config.ConfigFactory
import zio.config.magnolia.Descriptor.descriptor
import zio.config.toSnakeCase
import zio.{ZIO, ZLayer}
import zio.config.typesafe.TypesafeConfig

case class AppConfig(
                    http: HttpConfig
                    )

case class HttpConfig(
                     host: String,
                     port: Int
                     )

object AppConfig {
  private val configDescriptor = descriptor[AppConfig].mapKey(toSnakeCase)

  val appConfigLayer: ZLayer[Any, Throwable, AppConfig] = TypesafeConfig
    .fromTypesafeConfig(ZIO.attempt(ConfigFactory.load()), AppConfig.configDescriptor)
    .mapError(err => new Throwable(err.getMessage(), err.getCause))
    .tapError(err => ZIO.logError(s"Error loading config: ${err.getMessage}"))
}