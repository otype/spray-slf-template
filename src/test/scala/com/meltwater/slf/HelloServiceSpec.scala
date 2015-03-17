package com.meltwater.slf

import com.meltwater.slf.api.hello.HelloService
import org.scalatest.{Matchers, WordSpec}
import spray.http.StatusCodes._
import spray.testkit.ScalatestRouteTest

class HelloServiceSpec extends WordSpec with Matchers with ScalatestRouteTest with HelloService {

  def actorRefFactory = system

  "HelloService" when {
    "requesting GET /hello/welcome" should {
      "return a greeting for GET requests to the path /hello/welcome" in {
        Get("/hello/welcome") ~> helloServiceRoute ~> check {
          responseAs[String] should contain("Say hello")
        }
      }

      "return a MethodNotAllowed error for PUT requests to the root path" in {
        Put() ~> sealRoute(helloServiceRoute) ~> check {
          status === MethodNotAllowed
          responseAs[String] === "HTTP method not allowed, supported methods: GET"
        }
      }
    }
  }
}

