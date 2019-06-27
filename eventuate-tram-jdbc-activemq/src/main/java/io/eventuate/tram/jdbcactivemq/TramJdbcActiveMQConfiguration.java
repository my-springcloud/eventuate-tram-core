package io.eventuate.tram.jdbcactivemq;

import io.eventuate.messaging.activemq.consumer.MessageConsumerActiveMQConfiguration;
import io.eventuate.tram.consumer.activemq.EventuateTramActiveMQMessageConsumerConfiguration;
import io.eventuate.tram.consumer.common.spring.TramConsumerCommonConfiguration;
import io.eventuate.tram.messaging.producer.jdbc.spring.TramMessageProducerJdbcConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({MessageConsumerActiveMQConfiguration.class,
        TramMessageProducerJdbcConfiguration.class,
        TramConsumerCommonConfiguration.class,
        EventuateTramActiveMQMessageConsumerConfiguration.class})
public class TramJdbcActiveMQConfiguration {
}
