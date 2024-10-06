package com.ebook_searching.ontology.pubsub.config;

import io.confluent.kafka.serializers.protobuf.KafkaProtobufDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.ebook_searching.common.constants.KafkaConstants;
import org.ebook_searching.proto.Event;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {
    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Value(value = "${spring.kafka.schema.registry.url}")
    private String schemaRegistryUrl;

    @Bean
    public ConsumerFactory<String, Event.AddBookEvent> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapAddress);
        props.put(
                ConsumerConfig.GROUP_ID_CONFIG,
                "foo");
        props.put(
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);
        props.put(
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                KafkaProtobufDeserializer.class);
        props.put(KafkaConstants.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryUrl);
//        KafkaProtobufDeserializer<GreetingWrapper.Greeting> deserializer = new KafkaProtobufDeserializer<>();
        // Specify the fully qualified name of the Protobuf class to deserialize into
        props.put("specific.protobuf.value.type", Event.AddBookEvent.class.getName());
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new KafkaProtobufDeserializer<Event.AddBookEvent>());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Event.AddBookEvent>
    kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Event.AddBookEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
