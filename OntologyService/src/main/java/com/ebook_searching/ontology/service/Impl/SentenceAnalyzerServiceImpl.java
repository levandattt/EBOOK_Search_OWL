package com.ebook_searching.ontology.service.Impl;

import com.ebook_searching.ontology.service.SentenceAnalyzerService;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Service
public class SentenceAnalyzerServiceImpl implements SentenceAnalyzerService {

    @Override
    public List<String> readListFromFile(String filePath) {
        List<String> dataList = new ArrayList<>();
        File file = new File(filePath);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                dataList.add(line.trim().toLowerCase());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dataList;
    }

    @Override
    public List<String> analyzeSentence(String sentence1) {
        String filePath = "OntologyService/src/main/resources/data/SentenceAnalyzeData/OntologyKeyword.txt";
        List<String> dataList = readListFromFile(filePath);
        List<String> keywordList = new ArrayList<>();
        String sentence = sentence1.concat(" ");
        System.out.println("Sentence: " + sentence);
        String[] words = sentence.toLowerCase().split("\\s+");

        for (int i = 0; i < words.length; i++) {
            for (int length = words.length - i; length > 0; length--) {
                String phrase = String.join(" ", Arrays.copyOfRange(words, i, i + length));
                if (dataList.contains(phrase)) {
                    keywordList.add(phrase);
                    break;
                }
            }
        }

        System.out.println("Keyword list:");
        for (String keyword : keywordList) {
            System.out.println(keyword);
        }

        return keywordList;
    }
}
