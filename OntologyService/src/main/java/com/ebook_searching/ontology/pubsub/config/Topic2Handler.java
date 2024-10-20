package com.ebook_searching.ontology.pubsub.config;

import com.ebook_searching.ontology.model.Book;
import com.ebook_searching.ontology.payload.QueryRes;
import com.ebook_searching.ontology.service.BookService;
import com.ebook_searching.ontology.service.OntologyService;
import org.ebook_searching.proto.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.List;

@EnableKafka
@Configuration
public class Topic2Handler {
    @Autowired
    private OntologyService ontologyService;

    @Value(value = "${spring.kafka.consumer.add-book-topic}")
    private String addBookTopic;

    @KafkaListener(topics = "add-book-topic", groupId = "foo")
    public void listenGroupFoo(Event.AddBookEvent message) {
        System.out.println("Received Message in group foo: " + message.toString());
        ontologyService.saveBook(message);
//
//        QueryRes<List<Book>> books = bookService.searchBooksByAuthor("hihi");

//        System.out.println(books);
    }
}
