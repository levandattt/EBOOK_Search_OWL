package com.ebook_searching.ontology.service;
import java.util.List;

public interface OntologyService {
    public void loadOntology();
    //    public String addClass(AddClassReq addClassReq);
    public String getClasses();
    public String query(List<String> list);
}
