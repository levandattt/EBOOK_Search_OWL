package com.ebook_searching.book.service.Impl;

import com.ebook_searching.book.service.BookService;
import com.ebook_searching.book.model.Book;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Override
    public List<Book> findAll() {
        return List.of();
    }
}
