package com.ebook_searching.ontology.pubsub;

import com.ebook_searching.ontology.service.OntologyService;
import org.ebook_searching.proto.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;

@EnableKafka
@Configuration
public class EventHandlers {
    @Autowired
    private OntologyService ontologyService;

    @Value(value = "${spring.kafka.consumer.add-book-topic}")
    private String addBookTopic;

    @Value(value = "${spring.kafka.consumer.add-author-topic}")
    private String addAuthorTopic;

    @KafkaListener(topics = "add-book-topic", groupId = "ontology-service", containerFactory = "bookKafkaListenerContainerFactory")
    public void listenGroupFoo(Event.AddBookEvent message) {
        System.out.println("Add new book: " + message.toString());
        ontologyService.saveBook(message);
    }

    @KafkaListener(topics = "update-book-topic", groupId = "ontology-service", containerFactory = "bookKafkaListenerContainerFactory")
    public void updateBook(Event.AddBookEvent message) {
        System.out.println("Update book: " + message.toString());
        ontologyService.updateBook(message);
    }

    @KafkaListener(topics = "delete-book-topic", groupId = "ontology-service", containerFactory = "bookKafkaListenerContainerFactory")
    public void deleteBook(Event.AddBookEvent message) {
        System.out.println("Delete book: " + message.toString());
        ontologyService.deleteBook(message);
    }

    @KafkaListener(topics = "add-author-topic", groupId = "ontology-service", containerFactory = "authorKafkaListenerContainerFactory")
    public void listenGroupFoo(Event.Author message) {
        System.out.println("Add author: " + message.toString());
        ontologyService.saveAuthor(message);
    }

    @KafkaListener(topics = "update-author-topic", groupId = "ontology-service", containerFactory = "authorKafkaListenerContainerFactory")
    public void updateAuthor(Event.Author message) {
        System.out.println("Update author: " + message.toString());
        ontologyService.updateAuthor(message);
    }

    @KafkaListener(topics = "delete-author-topic", groupId = "ontology-service", containerFactory = "authorKafkaListenerContainerFactory")
    public void deleteAuthor(Event.Author message) {
        System.out.println("Delete author: " + message.toString());
        ontologyService.deleteAuthor(message);
    }
}
