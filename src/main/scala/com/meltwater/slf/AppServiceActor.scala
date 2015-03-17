package com.meltwater.slf

import akka.actor.Actor
import com.meltwater.slf.api.{SwaggerHttpServiceConfig, Routes}
import com.meltwater.slf.api.status.StatusService

import scala.reflect.runtime.universe._

class AppServiceActor extends Actor with Routes {

  def actorRefFactory = context

  val hookedServicesInSwagger = new SwaggerHttpServiceConfig(
    context, /* Actor reference */
    Seq(/* List of Services to hook up to Swagger interface */
      typeOf[StatusService]
    )
  )

  def receive = runRoute(hookedServicesInSwagger.routes ~ allRoutes)
}
