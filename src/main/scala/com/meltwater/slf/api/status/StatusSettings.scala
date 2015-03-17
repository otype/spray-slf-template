package com.meltwater.slf.api.status

import akka.actor.ActorSystem
import akka.event.{Logging, LoggingAdapter}
import com.meltwater.slf.{BuildInfo, AppConfig}
import spray.http.{StatusCode, StatusCodes}

object StatusSettings {
  val myActorSystem = ActorSystem(AppConfig.AppSettingsConfig.defaultActorSystemName)
  val log: LoggingAdapter = Logging.getLogger(myActorSystem, StatusSettings.getClass)
  var code: StatusCode = StatusCodes.OK

  def buildStatusMessage(statusCode: StatusCode) = StatusMessage(
    "Welcome to MyApp.",
    BuildInfo.version,
    statusCode.intValue,
    BuildInfo.sbtVersion,
    BuildInfo.scalaVersion
  )

  def validate(statusCode: Int): StatusCode = {
    StatusCodes.getForKey(statusCode).getOrElse(StatusCodes.BadRequest)
  }

  def generateResponse() = {
    val statusMessage: StatusMessage = buildStatusMessage(code)
    code.intValue match {
      case c if 200 until 599 contains c => (code, statusMessage)
      case _ => (StatusCodes.BadRequest, statusMessage)
    }
  }
}
