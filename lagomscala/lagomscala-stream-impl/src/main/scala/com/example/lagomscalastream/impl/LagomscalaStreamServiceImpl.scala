package com.example.lagomscalastream.impl

import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.example.lagomscalastream.api.LagomscalaStreamService
import com.example.lagomscala.api.LagomscalaService

import scala.concurrent.Future

/**
  * Implementation of the LagomscalaStreamService.
  */
class LagomscalaStreamServiceImpl(lagomscalaService: LagomscalaService) extends LagomscalaStreamService {
  def stream = ServiceCall { hellos =>
    Future.successful(hellos.mapAsync(8)(lagomscalaService.hello(_).invoke()))
  }
}
