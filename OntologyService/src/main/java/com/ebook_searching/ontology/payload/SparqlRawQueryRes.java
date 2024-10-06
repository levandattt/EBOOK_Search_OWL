package com.ebook_searching.ontology.payload;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Data
@Getter
@Setter
@Builder
public class SparqlRawQueryRes {
    private String rawQueryRes;
    private Map<String, Object> rawQueryData;
}
