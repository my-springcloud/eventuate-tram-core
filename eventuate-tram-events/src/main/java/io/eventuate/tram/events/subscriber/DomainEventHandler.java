package io.eventuate.tram.events.subscriber;

import io.eventuate.tram.events.common.DomainEvent;
import io.eventuate.tram.events.common.EventMessageHeaders;
import io.eventuate.tram.messaging.common.Message;

import java.util.function.Consumer;

/**
 * 领域事件处理器
 */
public class DomainEventHandler {
  /** 领域聚合对象的全限定类名 */
  private String aggregateType;
  /** 领域聚合对象支持的全部领域事件 */
  private final Class<DomainEvent> eventClass;
  /** 领域事件处理逻辑 */
  private final Consumer<DomainEventEnvelope<DomainEvent>> handler;

  public DomainEventHandler(String aggregateType, Class<DomainEvent> eventClass, Consumer<DomainEventEnvelope<DomainEvent>> handler) {
    this.aggregateType = aggregateType;
    this.eventClass = eventClass;
    this.handler = handler;
  }

  public boolean handles(Message message) {
    return aggregateType.equals(message.getRequiredHeader(EventMessageHeaders.AGGREGATE_TYPE))
            && eventClass.getName().equals(message.getRequiredHeader(EventMessageHeaders.EVENT_TYPE));
  }

  /**
   * 执行具体的事件处理逻辑
   * @param dee
   */
  public void invoke(DomainEventEnvelope<DomainEvent> dee) {
    handler.accept(dee);
  }

  public Class<DomainEvent> getEventClass() {
    return eventClass;
  }

  public String getAggregateType() {
    return aggregateType;
  }
}
