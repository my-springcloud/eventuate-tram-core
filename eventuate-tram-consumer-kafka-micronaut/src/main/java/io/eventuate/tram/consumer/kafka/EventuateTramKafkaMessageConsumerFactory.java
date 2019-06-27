package io.eventuate.tram.consumer.kafka;

import io.eventuate.messaging.kafka.consumer.MessageConsumerKafkaImpl;
import io.eventuate.tram.consumer.common.MessageConsumerImplementation;
import io.micronaut.context.annotation.Factory;

import javax.inject.Singleton;

@Factory
public class EventuateTramKafkaMessageConsumerFactory {
  @Singleton
  public MessageConsumerImplementation messageConsumerImplementation(MessageConsumerKafkaImpl messageConsumerKafka) {
    return new EventuateTramKafkaMessageConsumer(messageConsumerKafka);
  }
}
