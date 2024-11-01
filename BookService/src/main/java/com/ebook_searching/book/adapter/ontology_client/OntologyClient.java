package com.ebook_searching.book.adapter.ontology_client;

import com.ebook_searching.book.payload.ListBooksResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

public interface OntologyClient {
    ListBooksResponse search(OntologySearchParams params    );
}
