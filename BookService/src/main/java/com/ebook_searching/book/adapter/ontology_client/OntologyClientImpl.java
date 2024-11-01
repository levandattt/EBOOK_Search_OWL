package com.ebook_searching.book.adapter.ontology_client;

import com.ebook_searching.book.mapper.AuthorMapper;
import com.ebook_searching.book.mapper.BookMapper;
import com.ebook_searching.book.payload.ListBooksResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OntologyClientImpl implements OntologyClient {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${app.gateway.url}")
    private String ontologyServiceBaseUrl;

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
                res.setBookDetail(bookMapper.toBookDetail(books.get(0)));
            } else {
                res.setData(books.stream().map(bookMapper::toBaseBook).collect(Collectors.toList()));
            }
        }

        if (author != null) {
            res.setAuthor(authorMapper.toAuthorDetail(ontologySearchRes.getAuthor()));
        }
        return
                res;
    }
}
