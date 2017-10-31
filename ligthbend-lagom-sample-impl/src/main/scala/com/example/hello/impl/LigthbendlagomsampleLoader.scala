package com.example.hello.impl

import com.lightbend.lagom.scaladsl.api.ServiceLocator
import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.persistence.cassandra.CassandraPersistenceComponents
import com.lightbend.lagom.scaladsl.server._
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import play.api.libs.ws.ahc.AhcWSComponents
import com.example.hello.api.LigthbendlagomsampleService
import com.lightbend.lagom.scaladsl.broker.kafka.LagomKafkaComponents
import com.softwaremill.macwire._

class LigthbendlagomsampleLoader extends LagomApplicationLoader {

  override def load(context: LagomApplicationContext): LagomApplication =
    new LigthbendlagomsampleApplication(context) {
      override def serviceLocator: ServiceLocator = NoServiceLocator
    }

  override def loadDevMode(context: LagomApplicationContext): LagomApplication =
    new LigthbendlagomsampleApplication(context) with LagomDevModeComponents

  override def describeService = Some(readDescriptor[LigthbendlagomsampleService])
}

abstract class LigthbendlagomsampleApplication(context: LagomApplicationContext)
  extends LagomApplication(context)
    with CassandraPersistenceComponents
    with LagomKafkaComponents
    with AhcWSComponents {

  // Bind the service that this server provides
  override lazy val lagomServer = serverFor[LigthbendlagomsampleService](wire[LigthbendlagomsampleServiceImpl])

  // Register the JSON serializer registry
  override lazy val jsonSerializerRegistry = LigthbendlagomsampleSerializerRegistry

  // Register the ligthbend-lagom-sample persistent entity
  persistentEntityRegistry.register(wire[LigthbendlagomsampleEntity])
}
