package org.ebook_searching.admin.controller;

import javax.validation.Valid;
import org.ebook_searching.admin.mapper.EventMapper;
import org.ebook_searching.admin.payload.request.AddBookRequest;
import org.ebook_searching.admin.payload.request.UpdateBookRequest;
import org.ebook_searching.admin.payload.response.AddBookResponse;
import org.ebook_searching.admin.payload.response.DeleteBookResponse;
import org.ebook_searching.admin.payload.response.UpdateBookResponse;
import org.ebook_searching.admin.service.BookService;
import org.ebook_searching.proto.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public class BookController {
    @Value(value = "${spring.kafka.consumer.add-book-topic}")
    private String addBookTopic;

    @Autowired
    private BookService bookService;

    @Autowired
    private EventMapper eventMapper;

    @Autowired
    private KafkaTemplate<String, Event.AddBookEvent> addBookEventPublisher;

    @PostMapping
    public AddBookResponse addABook(@Valid @RequestBody AddBookRequest book) {
//        addBookEventPublisher.send(addBookTopic,
//                eventMapper.toAddBookEvent(book));

        return bookService.addBook(book);
    }

    public UpdateBookResponse updateBook(@Valid @RequestBody UpdateBookRequest req) {
        return bookService.updateBook(req);
    }

    @DeleteMapping("/{id}")
    public DeleteBookResponse deleteBook(@Valid @PathVariable("id") Long id) {
        return bookService.deleteBook(id);
    }
}
