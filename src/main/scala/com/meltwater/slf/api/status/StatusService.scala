package com.meltwater.slf.api.status

import akka.actor.ActorSystem
import akka.event.{Logging, LoggingAdapter}
import com.meltwater.slf.AppConfig
import com.meltwater.slf.api.Json4sSupport
import com.wordnik.swagger.annotations.{ApiImplicitParams, ApiImplicitParam, Api, ApiOperation}

//import com.wordnik.swagger.annotations.{Api, ApiImplicitParam, ApiImplicitParams, ApiOperation}

import spray.routing._

@Api(value = "/_status", description = "Status operations.")
trait StatusService extends HttpService {

  private val myActorSystem = ActorSystem(AppConfig.AppSettingsConfig.defaultActorSystemName)

  private val log: LoggingAdapter = Logging.getLogger(myActorSystem, StatusService.this)
  val statusServiceRoutes: Route = path("_status") {
    getStatus ~ postStatus
  }

  @ApiOperation(
    httpMethod = "GET",
    produces = "application/json",
    value = "Get Hägar status.",
    notes = "Retrieve the service's current status code. Also provides additional version information for sbt, scala, etc.",
    nickname = "getStatus"
  )
  def getStatus = get {
    detach() {
      import Json4sSupport._
      complete(StatusSettings.generateResponse())
    }
  }

  @ApiOperation(
    httpMethod = "POST",
    consumes = "application/json, application/x-www-form-urlencoded",
    produces = "application/json",
    value = "Set Hägar status (for load balancer manipulation).",
    notes = "This resource offers a way to manually manipulate the service's status code. E.g. manually setting the status code to 503 will cause related load balancers (with activated health check) to register this service as unavailable, thus, preventing future requests to be directed to the given running instance (rolling upgrade scenario).\n\nThis feature is primarily for Meltwater OPS.",
    nickname = "postStatus"
  )
  @ApiImplicitParams(
    Array(
      new ApiImplicitParam(name = "statusCode", value = "Status code setting.", required = true, dataType = "Int", paramType = "form")
    )
  )
  def postStatus = post {
    formFields('statusCode.as[Int]) { (statusCode: Int) =>
      detach() {
        import Json4sSupport._
        log.info(s"Manual override! Setting status code={}", statusCode)
        StatusSettings.code = StatusSettings.validate(statusCode)
        complete(StatusSettings.generateResponse())
      }
    }
  }
}
