package io.eventuate.tram.events.subscriber;

import io.eventuate.tram.messaging.common.Message;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

/**
 * 领域事件处理容器
 */
public class DomainEventHandlers {
  private List<DomainEventHandler> handlers;

  public DomainEventHandlers(List<DomainEventHandler> handlers) {
    this.handlers = handlers;
  }

  /**
   * 获取支持的所有领域聚合对象
   * @return
   */
  public Set<String> getAggregateTypesAndEvents() {
    return handlers.stream().map(DomainEventHandler::getAggregateType).collect(toSet());
  }

  public List<DomainEventHandler> getHandlers() {
    return handlers;
  }

  public Optional<DomainEventHandler> findTargetMethod(Message message) {
    return handlers.stream().filter(h -> h.handles(message)).findFirst();
  }


}
