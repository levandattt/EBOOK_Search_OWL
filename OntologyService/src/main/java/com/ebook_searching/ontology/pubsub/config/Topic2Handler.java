package com.ebook_searching.ontology.pubsub.config;

import com.ebook_searching.ontology.service.OntologyService;
import org.ebook_searching.proto.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;

@EnableKafka
@Configuration
public class Topic2Handler {
    @Autowired
    private OntologyService ontologyService;

    @Value(value = "${spring.kafka.consumer.add-book-topic}")
    private String addBookTopic;

    @Value(value = "${spring.kafka.consumer.add-author-topic}")
    private String addAuthorTopic;

    @KafkaListener(topics = "add-book-topic", groupId = "foo", containerFactory = "bookKafkaListenerContainerFactory")
    public void listenGroupFoo(Event.AddBookEvent message) {
        System.out.println("Received Message in group foo: " + message.toString());
        ontologyService.saveBook(message);
    }

    @KafkaListener(topics = "add-author-topic", groupId = "foo", containerFactory = "authorKafkaListenerContainerFactory")
    public void listenGroupFoo(Event.Author message) {
        System.out.println("Received Message in group foo: " + message.toString());
        ontologyService.saveAuthor(message);
    }
}
