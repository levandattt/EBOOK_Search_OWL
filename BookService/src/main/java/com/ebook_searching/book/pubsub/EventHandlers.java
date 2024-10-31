package com.ebook_searching.book.pubsub;

import com.ebook_searching.book.service.AuthorService;
import com.ebook_searching.book.service.BookService;
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
    private BookService bookService;

    @Autowired
    private AuthorService authorService;

    @Value(value = "${spring.kafka.consumer.add-book-topic}")
    private String addBookTopic;

    @Value(value = "${spring.kafka.consumer.add-author-topic}")
    private String addAuthorTopic;

    @KafkaListener(topics = "add-book-topic", groupId = "book-service", containerFactory = "bookKafkaListenerContainerFactory")
    public void listenGroupFoo(Event.AddBookEvent message) {
        System.out.println("Received Message in group foo: " + message.toString());
        bookService.addBook(message);
    }

    @KafkaListener(topics = "add-author-topic", groupId = "book-service", containerFactory = "authorKafkaListenerContainerFactory")
    public void listenGroupFoo(Event.Author message) {
        System.out.println("Received Message in group foo: " + message.toString());
        authorService.addAuthor(message);
    }
}
