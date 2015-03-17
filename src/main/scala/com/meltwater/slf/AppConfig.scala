package com.meltwater.slf

import com.typesafe.config.{Config, ConfigFactory}

object AppConfig {
  private val config = ConfigFactory.load()

  object SprayCanConfig {
    private val sprayCanConfig: Config = config.getConfig("spray.can")
    private val serverConfig: Config = sprayCanConfig.getConfig("server")
    private val clientConfig: Config = sprayCanConfig.getConfig("client")
    private val hostConnectorConfig: Config = sprayCanConfig.getConfig("host-connector")
    lazy val serverRemoteAddressHeader = serverConfig.getString("remote-address-header")
    lazy val clientUserAgentHeader = clientConfig.getString("user-agent-header")
    lazy val hostConnectorMaxConnections = hostConnectorConfig.getString("max-connections")
    lazy val hostConnectorMaxRetries = hostConnectorConfig.getString("max-retries")
    lazy val hostConnectorMaxRedirects = hostConnectorConfig.getString("max-redirects")
    lazy val hostConnectorPipelining = hostConnectorConfig.getString("pipelining")
  }

  object SprayRoutingConfig {
    private val sprayRoutingConfig: Config = config.getConfig("spray.routing")
    private val usersConfig: Config = sprayRoutingConfig.getConfig("users")
    lazy val usersMobileTesting = usersConfig.getString("mobile.testing")
  }

  object ServiceConfig {
    private val serviceConfig: Config = config.getConfig("service")
    lazy val address = serviceConfig.getString("address")
    lazy val port = serviceConfig.getInt("port")

    object CacheResponseHeaders {
      private val cacheResponseHeadersConfig = serviceConfig.getConfig("cacheResponseHeaders")
      lazy val cacheControl = cacheResponseHeadersConfig.getString("cacheControl")
    }
  }

  object AppSettingsConfig {
    private val appSettingsConfig = config.getConfig("app")
    lazy val defaultActorSystemName = appSettingsConfig.getString("defaultActorSystem")
    lazy val actorSystemRefName = appSettingsConfig.getString("actorSystemRefName")
    lazy val version = appSettingsConfig.getInt("version")
  }

  object SwaggerConfig {
    private val swaggerConfig = config.getConfig("swagger")
    lazy val version = swaggerConfig.getString("version")
    lazy val baseUrl = swaggerConfig.getString("baseUrl")
    lazy val docsPath = swaggerConfig.getString("docsPath")
    lazy val shortName = swaggerConfig.getString("shortName")
    lazy val title = swaggerConfig.getString("title")
    lazy val description = swaggerConfig.getString("description")
    lazy val termsOfServiceUrl = swaggerConfig.getString("termsOfServiceUrl")
    lazy val contact = swaggerConfig.getString("contact")
    lazy val license = swaggerConfig.getString("license")
    lazy val licenseUrl = swaggerConfig.getString("licenseUrl")
  }
}
