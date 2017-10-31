package com.example.hellostream.api

import akka.NotUsed
import akka.stream.scaladsl.Source
import com.lightbend.lagom.scaladsl.api.{Service, ServiceCall}

/**
  * The ligthbend-lagom-sample stream interface.
  *
  * This describes everything that Lagom needs to know about how to serve and
  * consume the LigthbendlagomsampleStream service.
  */
trait LigthbendlagomsampleStreamService extends Service {

  def stream: ServiceCall[Source[String, NotUsed], Source[String, NotUsed]]

  override final def descriptor = {
    import Service._

    named("ligthbend-lagom-sample-stream")
      .withCalls(
        namedCall("stream", stream)
      ).withAutoAcl(true)
  }
}

