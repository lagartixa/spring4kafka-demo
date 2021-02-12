package com.aerialdev.kafka.spring4kafkademo.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopics {

    public static final String CAR_TOPIC = "car.events";
    public static final String PRICE_TOPIC = "price.events";
    public static final String COLOR_TOPIC = "colour.events";

    @Bean
    public NewTopic carEventsTopic() {
        return TopicBuilder.name(CAR_TOPIC)
                .partitions(2)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic colourEventsTopic() {
        return TopicBuilder.name(COLOR_TOPIC)
                .partitions(2)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic priceEventsTopic() {
        return TopicBuilder.name(PRICE_TOPIC)
                .partitions(2)
                .replicas(1)
                .build();
    }

}
