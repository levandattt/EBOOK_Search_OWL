package com.ebook_searching.book.controller;

import com.ebook_searching.book.dto.AuthorDetail;
import com.ebook_searching.book.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @GetMapping("/{id}")
    public AuthorDetail getAuthorDetail(@PathVariable Long id) {
        return authorService.findAuthorDetailById(id);
    }
}
