package com.meltwater.slf.api

import akka.actor.ActorSystem
import akka.event.Logging
import com.meltwater.slf.AppConfig
import com.meltwater.slf.api.hello.HelloService
import com.meltwater.slf.api.status.StatusService
import spray.http.HttpHeaders.RawHeader
import spray.routing.authentication.BasicAuth

import scala.concurrent.ExecutionContextExecutor

trait Routes extends StatusService with HelloService {
  val myActorSystem = ActorSystem(AppConfig.AppSettingsConfig.defaultActorSystemName)

  implicit def executionContext: ExecutionContextExecutor = myActorSystem.dispatcher

  val securityRealm = "MyApp Swagger"
  val respondWithCacheHeaders = respondWithSingletonHeaders(
    List(
      RawHeader("Cache-Control", AppConfig.ServiceConfig.CacheResponseHeaders.cacheControl)
    )
  )

  /* "/_status" */
  val statusRoutes = logRequest("Status", Logging.DebugLevel) {
    statusServiceRoutes
  }

  /* "/ ..." */
  val appRoutes = logRequest("Some Fake Operations", Logging.DebugLevel) {
//    pathPrefix("") {
//      pathEndOrSingleSlash {
//        respondWithCacheHeaders {
          helloServiceRoute
//        }
//      }
//    }
  }

  val swaggerUiRoutes = get {
    pathPrefix("") {
      pathEndOrSingleSlash {
        authenticate(BasicAuth(realm = securityRealm)) { userName =>
          getFromResource("swagger-ui/index.html")
        }
      }
    } ~
      getFromResourceDirectory("swagger-ui")
  }

  /* ALL ROUTES COMBINED */
  val allRoutes = statusRoutes ~ appRoutes ~ swaggerUiRoutes
}