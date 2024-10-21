package com.ebook_searching.ontology.model.Ontology;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ObjectProperty {
    private String name;
    private String domain;
    private String range;
}
