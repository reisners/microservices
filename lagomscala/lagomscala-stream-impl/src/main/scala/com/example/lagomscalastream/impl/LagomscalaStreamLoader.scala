package com.example.lagomscalastream.impl

import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.server._
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import play.api.libs.ws.ahc.AhcWSComponents
import com.example.lagomscalastream.api.LagomscalaStreamService
import com.example.lagomscala.api.LagomscalaService
import com.softwaremill.macwire._

class LagomscalaStreamLoader extends LagomApplicationLoader {

  override def load(context: LagomApplicationContext): LagomApplication =
    new LagomscalaStreamApplication(context) {
      override def serviceLocator = NoServiceLocator
    }

  override def loadDevMode(context: LagomApplicationContext): LagomApplication =
    new LagomscalaStreamApplication(context) with LagomDevModeComponents

  override def describeServices = List(
    readDescriptor[LagomscalaStreamService]
  )
}

abstract class LagomscalaStreamApplication(context: LagomApplicationContext)
  extends LagomApplication(context)
    with AhcWSComponents {

  // Bind the service that this server provides
  override lazy val lagomServer = serverFor[LagomscalaStreamService](wire[LagomscalaStreamServiceImpl])

  // Bind the LagomscalaService client
  lazy val lagomscalaService = serviceClient.implement[LagomscalaService]
}
