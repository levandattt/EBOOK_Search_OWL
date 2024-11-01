package com.ebook_searching.book.adapter.ontology_client;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OntologySearchParams {
    private String keyword;
    private int limit ;
    private int offset ;
    private String orderBy;
    private String orderDirection;
}
