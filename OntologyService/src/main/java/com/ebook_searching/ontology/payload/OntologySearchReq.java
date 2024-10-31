package com.ebook_searching.ontology.payload;

import lombok.Data;

@Data
public class OntologySearchReq {
    private String keyword;
    private int limit ;
    private int offset ;
    private String orderBy;
    private String orderDirection;
}
