package com.example.pubsubframework;

import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * A pluggable and reusable pub-sub framework using Spring Cloud Stream
 * with Kafka as the message broker. Designed to simplify event-driven
 * microservice development.
 */

public class PubSubFramework {

    /**
     * Sample event class representing a generic pub-sub message.
     */
    public static class PubSubEvent {
        private String eventType;
        private String payload;

        public PubSubEvent() {
        }

        public PubSubEvent(String eventType, String payload) {
            this.eventType = eventType;
            this.payload = payload;
        }

        public String getEventType() {
            return eventType;
        }

        public void setEventType(String eventType) {
            this.eventType = eventType;
        }

        public String getPayload() {
            return payload;
        }

        public void setPayload(String payload) {
            this.payload = payload;
        }
    }

    /**
     * Functional interface bean for processing events in a stream.
     */
    @Bean
    public Function<KStream<String, PubSubEvent>, KStream<String, PubSubEvent>> pubSubProcessor() {
        return input -> input
                .peek((key, value) -> System.out.printf("[RECEIVED] key=%s, value=%s\n", key, value.getPayload()))
                .mapValues(event -> {
                    // Add custom logic here
                    event.setPayload(event.getPayload().toUpperCase());
                    return event;
                });
    }

    /**
     * Helper to publish events (can be used in services)
     */
    @Component
    public static class EventPublisher {

        private final org.springframework.messaging.MessageChannel output;

        public EventPublisher(Processor processor) {
            this.output = processor.output();
        }

        public void publish(PubSubEvent event) {
            Message<PubSubEvent> message = org.springframework.messaging.support.MessageBuilder
                    .withPayload(event)
                    .build();
            output.send(message);
        }
    }

    /**
     * Example subscriber implementation
     */
    @Component
    @EnableBinding(Processor.class)
    public static class ExampleSubscriber {

        @StreamListener(Processor.INPUT)
        public void handleEvent(PubSubEvent event) {
            System.out.println("[SUBSCRIBER] Received: " + event.getPayload());
        }
    }
}
