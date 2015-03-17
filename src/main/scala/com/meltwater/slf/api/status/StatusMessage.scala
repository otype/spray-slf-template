package com.meltwater.slf.api.status

case class StatusMessage(message: String, version: String, statusCode: Int, sbtVersion: String, scalaVersion: String)
