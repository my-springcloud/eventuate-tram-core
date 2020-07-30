package io.eventuate.tram.events.subscriber;

import io.eventuate.common.json.mapper.JSonMapper;
import io.eventuate.tram.events.common.DomainEvent;
import io.eventuate.tram.events.common.DomainEventNameMapping;
import io.eventuate.tram.events.common.EventMessageHeaders;
import io.eventuate.tram.messaging.common.Message;
import io.eventuate.tram.messaging.consumer.MessageConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.Optional;

/**
 * 消息中间件的每个订阅端都有一个事件派发器，会将消息从消息中间件接受到的消息派发到已经注册的事件处理器中
 */
public class DomainEventDispatcher {

  private Logger logger = LoggerFactory.getLogger(getClass());

  private final String eventDispatcherId;
  /** 事件处理器的容器 */
  private DomainEventHandlers domainEventHandlers;
  /** 该消息消费端从消息中间件接受到的所有的消息都会转换成 DomainEventEnvelope 通过本派发器派发事件处理器 */
  private MessageConsumer messageConsumer;
  /** 将 Message.headers.event-type 转换成 领域事件（DomainEvent）的全限定名称*/
  private DomainEventNameMapping domainEventNameMapping;

  public DomainEventDispatcher(String eventDispatcherId, DomainEventHandlers domainEventHandlers, MessageConsumer messageConsumer, DomainEventNameMapping domainEventNameMapping) {
    this.eventDispatcherId = eventDispatcherId;
    this.domainEventHandlers = domainEventHandlers;
    this.messageConsumer = messageConsumer;
    this.domainEventNameMapping = domainEventNameMapping;
  }

  @PostConstruct
  public void initialize() {
    logger.info("Initializing domain event dispatcher");
    messageConsumer.subscribe(eventDispatcherId, domainEventHandlers.getAggregateTypesAndEvents(), this::messageHandler);
    logger.info("Initialized domain event dispatcher");
  }

  public void messageHandler(Message message) {
    String aggregateType = message.getRequiredHeader(EventMessageHeaders.AGGREGATE_TYPE);

    // 将 message.headers.event-type 设置成 EventClassName
    message.setHeader(
            EventMessageHeaders.EVENT_TYPE,
            domainEventNameMapping.externalEventTypeToEventClassName(
                    aggregateType,
                    message.getRequiredHeader(EventMessageHeaders.EVENT_TYPE)
            )
    );
    // 获取事件处理器
    Optional<DomainEventHandler> handler = domainEventHandlers.findTargetMethod(message);

    if (!handler.isPresent()) {
      return;
    }

    DomainEvent param = JSonMapper.fromJson(message.getPayload(), handler.get().getEventClass());

    // 调用消息处理器处理消息
    handler.get().invoke(
            new DomainEventEnvelopeImpl<>(message,
            aggregateType,
            message.getRequiredHeader(EventMessageHeaders.AGGREGATE_ID),
            message.getRequiredHeader(Message.ID),
            param)
    );
  }
}
