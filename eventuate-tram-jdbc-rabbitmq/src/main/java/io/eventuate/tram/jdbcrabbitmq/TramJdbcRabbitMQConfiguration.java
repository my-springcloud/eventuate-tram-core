package io.eventuate.tram.jdbcrabbitmq;

import io.eventuate.messaging.rabbitmq.consumer.MessageConsumerRabbitMQConfiguration;
import io.eventuate.tram.consumer.common.spring.TramConsumerCommonConfiguration;
import io.eventuate.tram.consumer.rabbitmq.EventuateTramRabbitMQMessageConsumerConfiguration;
import io.eventuate.tram.messaging.producer.jdbc.spring.TramMessageProducerJdbcConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({MessageConsumerRabbitMQConfiguration.class,
        TramMessageProducerJdbcConfiguration.class,
        TramConsumerCommonConfiguration.class,
        EventuateTramRabbitMQMessageConsumerConfiguration.class})
public class TramJdbcRabbitMQConfiguration {
}
