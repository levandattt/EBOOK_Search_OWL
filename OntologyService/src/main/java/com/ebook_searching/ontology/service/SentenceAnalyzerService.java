package com.ebook_searching.ontology.service;

import java.util.List;

public interface SentenceAnalyzerService {


    List<String> readListFromFile(String filePath);

    List<String> analyzeSentence(String sentence);
}
