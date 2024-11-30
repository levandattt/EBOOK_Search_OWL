package org.ebook_searching.admin.controller;

import org.ebook_searching.admin.dto.AuthorDetail;
import org.ebook_searching.admin.dto.BookDetail;
import org.ebook_searching.admin.payload.request.AddAuthorRequest;
import org.ebook_searching.admin.payload.request.UpdateAuthorRequest;
import org.ebook_searching.admin.payload.response.AddAuthorResponse;
import org.ebook_searching.admin.payload.response.DeleteAuthorResponse;
import org.ebook_searching.admin.payload.response.GetAuthorResponse;
import org.ebook_searching.admin.payload.response.UpdateAuthorResponse;
import org.ebook_searching.admin.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @PostMapping
    public AddAuthorResponse addAAuthor(@Valid @RequestBody AddAuthorRequest book) {
        return authorService.addAuthor();
    }

    @PutMapping
    public UpdateAuthorResponse updateAuthor(@Valid @RequestBody UpdateAuthorRequest req) {
        return authorService.updateAuthor(req);
    }

    @DeleteMapping("/{id}")
    public DeleteAuthorResponse deleteAuthor(@Valid @PathVariable("id") Long id) {
        return authorService.deleteAuthor(id);
    }

    @GetMapping("/{id}")
    public AuthorDetail getAuthorDetail(@PathVariable Long id) {
        return authorService.findAuthorDetailById(id);
    }

    @GetMapping
    public List<GetAuthorResponse> getPaginatedAuthors() {
        return authorService.getAllAuthors();
    }
}
