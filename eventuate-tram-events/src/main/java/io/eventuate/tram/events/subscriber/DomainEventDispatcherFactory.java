package io.eventuate.tram.events.subscriber;

import io.eventuate.tram.events.common.DomainEventNameMapping;
import io.eventuate.tram.messaging.consumer.MessageConsumer;

public class DomainEventDispatcherFactory {

  /**
   * 消息消费端，每个消息消费端都订阅了消息中间件
   */
  protected MessageConsumer messageConsumer;
  protected DomainEventNameMapping domainEventNameMapping;

  public DomainEventDispatcherFactory(MessageConsumer messageConsumer, DomainEventNameMapping domainEventNameMapping) {
    this.messageConsumer = messageConsumer;
    this.domainEventNameMapping = domainEventNameMapping;
  }

  public DomainEventDispatcher make(String eventDispatcherId, DomainEventHandlers domainEventHandlers) {
    return new DomainEventDispatcher(eventDispatcherId, domainEventHandlers, messageConsumer, domainEventNameMapping);
  }
}
