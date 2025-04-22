package org.sk_softech.Bot1.config

import org.apache.kafka.clients.admin.NewTopic
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.config.TopicBuilder
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.listener.ContainerProperties

@Configuration
class KafkaConfig {

    @Bean
    fun kafkaListenerContainerFactory(
        consumerFactory: ConsumerFactory<String, String>
    ): ConcurrentKafkaListenerContainerFactory<String, String> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, String>()

        factory.consumerFactory = consumerFactory
        factory.isBatchListener = true                          // Enable batch listening
        factory.containerProperties.ackMode = ContainerProperties.AckMode.MANUAL // Manual ack required!
        factory.containerProperties.pollTimeout = 3000L         // Optional: tweak poll timeout if needed

        return factory
    }

    // Optional: define topic here if needed
    @Bean
    fun senderTopic(): NewTopic {
        return TopicBuilder.name("sender")
            .partitions(1)
            .replicas(1)
            .build()
    }
}
