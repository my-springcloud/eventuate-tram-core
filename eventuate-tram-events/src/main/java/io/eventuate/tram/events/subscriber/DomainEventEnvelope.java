package io.eventuate.tram.events.subscriber;

import io.eventuate.tram.events.common.DomainEvent;
import io.eventuate.tram.messaging.common.Message;

/**
 * 领域事件信封，用于将接受到的Message转换成 DomainEventEnvelope
 * @param <T>
 */
public interface DomainEventEnvelope<T extends DomainEvent> {
  String getAggregateId();
  Message getMessage();
  String getAggregateType();
  String getEventId();

  T getEvent();
}
