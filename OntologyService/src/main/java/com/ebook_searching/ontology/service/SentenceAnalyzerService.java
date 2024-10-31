package com.ebook_searching.ontology.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface SentenceAnalyzerService {


    List<String> readListFromFile(String filePath);

    List<String> analyzeSentence(String sentence);
}
