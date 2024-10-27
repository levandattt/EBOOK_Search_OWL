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
public class QueryRes<T> {
    private T data;
    private Map<String, Object> rawQueryData;
}
