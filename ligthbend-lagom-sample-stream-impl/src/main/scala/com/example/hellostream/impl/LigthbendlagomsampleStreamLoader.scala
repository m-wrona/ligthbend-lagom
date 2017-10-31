package com.example.hellostream.impl

import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.server._
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import play.api.libs.ws.ahc.AhcWSComponents
import com.example.hellostream.api.LigthbendlagomsampleStreamService
import com.example.hello.api.LigthbendlagomsampleService
import com.softwaremill.macwire._

class LigthbendlagomsampleStreamLoader extends LagomApplicationLoader {

  override def load(context: LagomApplicationContext): LagomApplication =
    new LigthbendlagomsampleStreamApplication(context) {
      override def serviceLocator = NoServiceLocator
    }

  override def loadDevMode(context: LagomApplicationContext): LagomApplication =
    new LigthbendlagomsampleStreamApplication(context) with LagomDevModeComponents

  override def describeService = Some(readDescriptor[LigthbendlagomsampleStreamService])
}

abstract class LigthbendlagomsampleStreamApplication(context: LagomApplicationContext)
  extends LagomApplication(context)
    with AhcWSComponents {

  // Bind the service that this server provides
  override lazy val lagomServer = serverFor[LigthbendlagomsampleStreamService](wire[LigthbendlagomsampleStreamServiceImpl])

  // Bind the LigthbendlagomsampleService client
  lazy val ligthbendlagomsampleService = serviceClient.implement[LigthbendlagomsampleService]
}
