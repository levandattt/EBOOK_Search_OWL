package com.ebook_searching.ontology.model.Ontology;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class OWLIndividual {
    private String individualName;
    private String className;
    private String individualURI;
    private String classURI;
    private String label;
}
