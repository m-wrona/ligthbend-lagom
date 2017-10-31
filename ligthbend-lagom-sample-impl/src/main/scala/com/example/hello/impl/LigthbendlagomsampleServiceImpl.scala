package com.example.hello.impl

import com.example.hello.api
import com.example.hello.api.{LigthbendlagomsampleService}
import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.lightbend.lagom.scaladsl.api.broker.Topic
import com.lightbend.lagom.scaladsl.broker.TopicProducer
import com.lightbend.lagom.scaladsl.persistence.{EventStreamElement, PersistentEntityRegistry}

/**
  * Implementation of the LigthbendlagomsampleService.
  */
class LigthbendlagomsampleServiceImpl(persistentEntityRegistry: PersistentEntityRegistry) extends LigthbendlagomsampleService {

  override def hello(id: String) = ServiceCall { _ =>
    // Look up the ligthbend-lagom-sample entity for the given ID.
    val ref = persistentEntityRegistry.refFor[LigthbendlagomsampleEntity](id)

    // Ask the entity the Hello command.
    ref.ask(Hello(id))
  }

  override def useGreeting(id: String) = ServiceCall { request =>
    // Look up the ligthbend-lagom-sample entity for the given ID.
    val ref = persistentEntityRegistry.refFor[LigthbendlagomsampleEntity](id)

    // Tell the entity to use the greeting message specified.
    ref.ask(UseGreetingMessage(request.message))
  }


  override def greetingsTopic(): Topic[api.GreetingMessageChanged] =
    TopicProducer.singleStreamWithOffset {
      fromOffset =>
        persistentEntityRegistry.eventStream(LigthbendlagomsampleEvent.Tag, fromOffset)
          .map(ev => (convertEvent(ev), ev.offset))
    }

  private def convertEvent(helloEvent: EventStreamElement[LigthbendlagomsampleEvent]): api.GreetingMessageChanged = {
    helloEvent.event match {
      case GreetingMessageChanged(msg) => api.GreetingMessageChanged(helloEvent.entityId, msg)
    }
  }
}
