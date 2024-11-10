package com.ebook_searching.book.adapter.ontology_client;

import com.ebook_searching.book.dto.BaseBook;
import com.ebook_searching.book.dto.BookDetail;
import com.ebook_searching.book.mapper.AuthorMapper;
import com.ebook_searching.book.mapper.BookMapper;
import com.ebook_searching.book.model.book.Book;
import com.ebook_searching.book.payload.ListBooksResponse;
import com.ebook_searching.book.repository.AuthorRepository;
import com.ebook_searching.book.repository.BookRepository;
import org.ebook_searching.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OntologyClientImpl implements OntologyClient {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${app.gateway.url}")
    private String ontologyServiceBaseUrl;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private AuthorMapper authorMapper;

    @Override
    public ListBooksResponse search(OntologySearchParams params) {
        String url = ontologyServiceBaseUrl + "/api/ontology/search?keyword={keyword}&limit={limit}&offset={offset}&orderBy={orderBy}&orderDirection={orderDirection}";

        Map<String, Object> urlParams = new HashMap<>();
        urlParams.put("keyword", params.getKeyword());
        urlParams.put("limit", params.getLimit());
        urlParams.put("offset", params.getOffset());
        urlParams.put("orderBy", params.getOrderBy());
        urlParams.put("orderDirection", params.getOrderDirection());

        ResponseEntity<OntologySearchRes> response = restTemplate.getForEntity(
                url,
                OntologySearchRes.class,
                urlParams);

        // For debugging
        OntologySearchRes ontologySearchRes = response.getBody();
        List<OWLBook> books = ontologySearchRes.getData();
        OWLAuthor author = ontologySearchRes.getAuthor();

        ListBooksResponse res = ListBooksResponse.builder()
                .numPages(ontologySearchRes.getNumPages())
                .totalItems(ontologySearchRes.getTotalItems())
                .limit(ontologySearchRes.getLimit())
                .offset(ontologySearchRes.getOffset()).build();

        if (books != null) {
            if (books.size() == 1) {
                OWLBook book = books.get(0);
                BookDetail bookDetail = bookMapper.toBookDetail(book);
                Optional<Book> savedBook = bookRepository.findByUuid(book.getUuid());
                savedBook.ifPresent(value -> bookDetail.setAuthors(value.getAuthors().stream().map(authorMapper::toAuthor).collect(Collectors.toList())));
                bookDetail.setLanguage(savedBook.get().getLanguage());
                bookDetail.setImage(savedBook.get().getImage());
                bookDetail.setPublisher(savedBook.get().getPublisher());
//                bookDetail.setGenres(StringUtils.toStringList(savedBook.get().getGenres()));
                res.setBookDetail(bookDetail);
            } else {
                List<BaseBook> baseBooks = books.stream().map(bookMapper::toBaseBook).toList() ;
                for (BaseBook book : baseBooks) {
                    Optional<Book> savedBook = bookRepository.findByUuid(book.getUuid());
                    book.setId(savedBook.get().getId());
                    book.setImage(savedBook.get().getImage());
                    savedBook.ifPresent(value -> book.setAuthors(value.getAuthors().stream().map(authorMapper::toAuthor).collect(Collectors.toList())));
                }
                res.setData(baseBooks);
            }
        }

        if (author != null) {
            res.setAuthor(authorMapper.toAuthorDetail(ontologySearchRes.getAuthor()));
        }
        return
                res;
    }
}
