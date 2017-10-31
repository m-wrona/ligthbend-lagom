package com.example.hellostream.impl

import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.example.hellostream.api.LigthbendlagomsampleStreamService
import com.example.hello.api.LigthbendlagomsampleService

import scala.concurrent.Future

/**
  * Implementation of the LigthbendlagomsampleStreamService.
  */
class LigthbendlagomsampleStreamServiceImpl(ligthbendlagomsampleService: LigthbendlagomsampleService) extends LigthbendlagomsampleStreamService {
  def stream = ServiceCall { hellos =>
    Future.successful(hellos.mapAsync(8)(ligthbendlagomsampleService.hello(_).invoke()))
  }
}
