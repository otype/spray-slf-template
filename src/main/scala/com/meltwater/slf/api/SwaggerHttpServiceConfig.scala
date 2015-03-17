package com.meltwater.slf.api

import akka.actor.ActorRefFactory
import com.gettyimages.spray.swagger.SwaggerHttpService
import com.meltwater.slf.{BuildInfo, AppConfig}
import com.wordnik.swagger.model.ApiInfo

import scala.reflect.runtime.universe.Type

class SwaggerHttpServiceConfig(context: ActorRefFactory, apiTypeList: Seq[Type]) extends SwaggerHttpService {
  override def actorRefFactory = context

  override def apiTypes: Seq[Type] = apiTypeList

  override def apiVersion = AppConfig.SwaggerConfig.version

  override def baseUrl = AppConfig.SwaggerConfig.baseUrl

  override def docsPath = AppConfig.SwaggerConfig.docsPath

  override def apiInfo = Some(
    new ApiInfo(
      AppConfig.SwaggerConfig.title + " v." + BuildInfo.version,
      AppConfig.SwaggerConfig.description,
      AppConfig.SwaggerConfig.termsOfServiceUrl,
      AppConfig.SwaggerConfig.contact,
      AppConfig.SwaggerConfig.license,
      AppConfig.SwaggerConfig.licenseUrl
    )
  )
}
