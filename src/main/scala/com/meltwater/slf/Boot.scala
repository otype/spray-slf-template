package com.meltwater.slf

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.event.{Logging, LoggingAdapter}
import akka.io.IO
import akka.pattern.ask
import akka.util.Timeout
import spray.can.Http

import scala.concurrent.duration._

object Boot extends App {
  implicit val myActorSystem = ActorSystem(AppConfig.AppSettingsConfig.defaultActorSystemName)

  val service: ActorRef = myActorSystem.actorOf(Props[AppServiceActor], AppConfig.AppSettingsConfig.actorSystemRefName)
  val log: LoggingAdapter = Logging.getLogger(myActorSystem, this.getClass)

  implicit val timeout = Timeout(5.seconds)
  log.info(s"Initializing ${AppConfig.SwaggerConfig.shortName} on Interface=${AppConfig.ServiceConfig.address} " +
    s"- Port=${AppConfig.ServiceConfig.port} " +
    s"- API base URL=/v${AppConfig.AppSettingsConfig.version}")
  IO(Http) ? Http.Bind(service, AppConfig.ServiceConfig.address, AppConfig.ServiceConfig.port)
}
