package controllers

import java.net.InetAddress

import javax.inject.{Inject, Singleton}
import play.api.Logging
import play.api.mvc.{AbstractController, ControllerComponents}

@Singleton
class TestController @Inject() (cc: ControllerComponents)
    extends AbstractController(cc)
    with Logging {

  def index = Action {
    Ok(InetAddress.getLocalHost.toString)
  }

}
