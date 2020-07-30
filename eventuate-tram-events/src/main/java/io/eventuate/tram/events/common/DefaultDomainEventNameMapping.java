package io.eventuate.tram.events.common;

/**
 * 将 DomainEvent 转化为 eventType 用于在Message.header中进行传递，在接收端，再将 eventType 转换回 DomainEvent类名用来反序列领域事件
 */
public class DefaultDomainEventNameMapping implements DomainEventNameMapping {

  /**
   * 将领域事件转换为 eventType，eventType可以设置到 Message.headers 中传递给消费端
   * @param aggregateType 领域对象的全限定类名
   * @param event 领域事件
   * @return
   */
  @Override
  public String eventToExternalEventType(String aggregateType, DomainEvent event) {
    return event.getClass().getName();
  }

  /**
   * 将eventType 转换为领域事件类型名称，eventType可以来自于 Message.headers
   * @param aggregateType
   * @param eventTypeHeader
   * @return
   */
  @Override
  public String externalEventTypeToEventClassName(String aggregateType, String eventTypeHeader) {
    return eventTypeHeader;
  }

}
