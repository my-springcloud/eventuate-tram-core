package io.eventuate.tram.events.publisher;

import io.eventuate.common.json.mapper.JSonMapper;
import io.eventuate.tram.events.common.DomainEvent;
import io.eventuate.tram.events.common.DomainEventNameMapping;
import io.eventuate.tram.events.common.EventMessageHeaders;
import io.eventuate.tram.messaging.common.Message;
import io.eventuate.tram.messaging.producer.MessageBuilder;
import io.eventuate.tram.messaging.producer.MessageProducer;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class DomainEventPublisherImpl implements DomainEventPublisher {

  /**
   * 消息生产者
   */
  private MessageProducer messageProducer;

  private DomainEventNameMapping domainEventNameMapping;

  public DomainEventPublisherImpl(MessageProducer messageProducer, DomainEventNameMapping domainEventNameMapping) {
    this.messageProducer = messageProducer;
    this.domainEventNameMapping = domainEventNameMapping;
  }

  @Override
  public void publish(String aggregateType, Object aggregateId, List<DomainEvent> domainEvents) {
    publish(aggregateType, aggregateId, Collections.emptyMap(), domainEvents);
  }

  /**
   * 将领域事件通过消息生产者发布出去
   * @param aggregateType 领域对象的全限定类名
   * @param aggregateId 领域对象的ID
   * @param headers
   * @param domainEvents 领域事件列表
   */
  @Override
  public void publish(String aggregateType, Object aggregateId, Map<String, String> headers, List<DomainEvent> domainEvents) {
    for (DomainEvent event : domainEvents) {
      // 发布领域事件
      messageProducer.send(aggregateType,
              makeMessageForDomainEvent(
                      aggregateType,
                      aggregateId,
                      headers,
                      event,
                      // 获取 EventMessageHeaders.EVENT_TYPE 信息 （领域事件的全限定类名）
                      domainEventNameMapping.eventToExternalEventType(aggregateType, event)
              )
      );

    }
  }

  public static Message makeMessageForDomainEvent(String aggregateType, Object aggregateId, Map<String, String> headers, DomainEvent event, String eventType) {
    // 将领域对象ID转换成字符串
    String aggregateIdAsString = aggregateId.toString();
    return MessageBuilder
            .withPayload(JSonMapper.toJson(event))
            .withExtraHeaders("", headers)
            // 使用了领域对象ID进行分区
            .withHeader(Message.PARTITION_ID, aggregateIdAsString)
            // 封装领域事件相关的头信息
            .withHeader(EventMessageHeaders.AGGREGATE_ID, aggregateIdAsString)
            .withHeader(EventMessageHeaders.AGGREGATE_TYPE, aggregateType)
            .withHeader(EventMessageHeaders.EVENT_TYPE, eventType)
            .build();
  }

}
