package com.meltwater.slf.api.hello

import akka.actor.ActorSystem
import akka.event.{Logging, LoggingAdapter}
import com.meltwater.slf.AppConfig
import com.wordnik.swagger.annotations.{ApiImplicitParams, Api, ApiOperation, ApiImplicitParam}
import spray.can.Http
import spray.http.MediaTypes._
import spray.routing._

@Api(value = "/hello", description = "Hello Resource (NEEDS WORK).")
trait HelloService extends HttpService {

  private val myActorSystem = ActorSystem(AppConfig.AppSettingsConfig.defaultActorSystemName)

  val log: LoggingAdapter = Logging.getLogger(myActorSystem, this)
  val helloServiceRoute: Route = getWelcomeMessage

  @ApiOperation(
    httpMethod = "GET",
    produces = "image/*",
    value = "Get an image for a provided image source URL.",
    notes = "Fetches an image from a given source URL. No image manipulation is applied.",
    nickname = "imageForUrl"
  )
  @ApiImplicitParams(
    Array(
      new ApiImplicitParam(name = "imageUrl", value = "Original image source URL.", required = true, dataType = "string", paramType = "query")
    )
  )
  def getWelcomeMessage: Route = get {
    path("hello" / "welcome") {
      println(Http.GetStats)

      respondWithMediaType(`text/html`) { // XML is marshalled to `text/xml` by default, so we simply override here
        complete {
          <html>
            <body>
              <h1>Say hello to <i>spray-routing</i> on <i>spray-can</i>!</h1>
            </body>
          </html>
        }
      }
    }
  }
}
