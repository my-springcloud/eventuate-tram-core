package io.eventuate.tram.events.publisher;

import io.eventuate.tram.events.common.DomainEvent;

import java.util.List;
import java.util.Map;

public interface DomainEventPublisher {

  /**
   * 在业务逻辑触发后，发布领域事件
   * @param aggregateType 领域对象的全限定类名
   * @param aggregateId 领域对象的ID
   * @param domainEvents 领域事件列表
   */
  void publish(String aggregateType, Object aggregateId, List<DomainEvent> domainEvents);

  void publish(String aggregateType, Object aggregateId, Map<String, String> headers, List<DomainEvent> domainEvents);

  default void publish(Class<?> aggregateType, Object aggregateId, List<DomainEvent> domainEvents) {
    publish(aggregateType.getName(), aggregateId, domainEvents);
  }
}
