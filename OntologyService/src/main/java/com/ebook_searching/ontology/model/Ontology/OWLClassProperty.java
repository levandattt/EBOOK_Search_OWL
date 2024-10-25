package com.ebook_searching.ontology.model.Ontology;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class OWLClassProperty {
    private String classURI;
    private String label;
    private String className;
}
