package com.ebook_searching.book.pubsub;

import com.ebook_searching.book.service.AuthorService;
import com.ebook_searching.book.service.BookService;
import com.ebook_searching.book.service.GenreService;
import com.ebook_searching.book.service.Impl.GenreServicImpl;
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

    @Autowired
    private GenreService genreService;

    @Value(value = "${spring.kafka.consumer.add-book-topic}")
    private String addBookTopic;

    @Value(value = "${spring.kafka.consumer.add-author-topic}")
    private String addAuthorTopic;
    @Autowired
    private GenreServicImpl genreServicImpl;

    @KafkaListener(topics = "add-book-topic", groupId = "book-service", containerFactory = "bookKafkaListenerContainerFactory")
    public void listenGroupFoo(Event.AddBookEvent message) {
        System.out.println("Received Message in group foo: " + message.toString());
        bookService.addBook(message);
    }

    @KafkaListener(topics = "update-book-topic", groupId = "book-service", containerFactory = "bookKafkaListenerContainerFactory")
    public void updateBook(Event.AddBookEvent message) {
        System.out.println("Received Message in group foo: " + message.toString());
        bookService.updateBook(message);
    }

    @KafkaListener(topics = "delete-book-topic", groupId = "book-service", containerFactory = "bookKafkaListenerContainerFactory")
    public void deleteBook(Event.AddBookEvent message) {
        System.out.println("Received Message in group foo: " + message.toString());
        bookService.deleteBook(message.getUuid());
    }

    @KafkaListener(topics = "add-author-topic", groupId = "book-service", containerFactory = "authorKafkaListenerContainerFactory")
    public void listenGroupFoo(Event.Author message) {
        System.out.println("Received Message in group foo: " + message.toString());
        authorService.addAuthor(message);
    }

    @KafkaListener(topics = "update-author-topic", groupId = "book-service", containerFactory = "authorKafkaListenerContainerFactory")
    public void updateAuthor(Event.Author message) {
        System.out.println("Received Message in group foo: " + message.toString());
        authorService.updateAuthor(message);
    }

    @KafkaListener(topics = "delete-author-topic", groupId = "book-service", containerFactory = "authorKafkaListenerContainerFactory")
    public void deleteAuthor(Event.Author message) {
        System.out.println("Received Message in group foo: " + message.toString());
        authorService.deleteAuthor(message.getUuid());
    }

    @KafkaListener(topics = "add-genre-topic", groupId = "book-service", containerFactory = "genreKafkaListenerContainerFactory")
    public void addGenre(Event.Genre message) {
        System.out.println("Yep, I'm here. Message: " + message.toString());
        genreService.addGenre(message);
    }
}
