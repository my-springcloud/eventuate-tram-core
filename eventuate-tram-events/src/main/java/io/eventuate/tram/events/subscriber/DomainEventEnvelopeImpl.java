package io.eventuate.tram.events.subscriber;

import io.eventuate.tram.events.common.DomainEvent;
import io.eventuate.tram.messaging.common.Message;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 信封默认实现
 * @param <T>
 */
public class DomainEventEnvelopeImpl<T extends DomainEvent> implements DomainEventEnvelope<T> {
  /** 接受到的消息 */
  private Message message;
  /** 领域对象全限定类名 */
  private final String aggregateType;
  /** 领域对象的ID */
  private String aggregateId;
  /** 事件ID */
  private final String eventId;
  /** 领域事件 */
  private T event;

  public DomainEventEnvelopeImpl(Message message, String aggregateType, String aggregateId, String eventId, T event) {
    this.message = message;
    this.aggregateType = aggregateType;
    this.aggregateId = aggregateId;
    this.eventId = eventId;
    this.event = event;
  }

  @Override
  public String getAggregateId() {
    return aggregateId;
  }

  @Override
  public Message getMessage() {
    return message;
  }

  public T getEvent() {
    return event;
  }


  @Override
  public String getAggregateType() {
    return aggregateType;
  }

  @Override
  public String getEventId() {
    return eventId;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
