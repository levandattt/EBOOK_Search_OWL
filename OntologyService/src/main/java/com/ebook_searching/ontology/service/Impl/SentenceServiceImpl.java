package com.ebook_searching.ontology.service.Impl;

import com.ebook_searching.ontology.service.OpenAIChatService;
import com.ebook_searching.ontology.service.SentenceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SentenceServiceImpl implements SentenceService {
    @Autowired
    private final OpenAIChatService openAIChatService;

    public SentenceServiceImpl(OpenAIChatService openAIChatService) {
        this.openAIChatService = openAIChatService;
    }

    @Override
    public List<String> analyzeSentence(String sentence) {
        String answer =  openAIChatService.chat("Split this sentence into three parts: subject, relationship, object.  Extract all main entity names as arrays for subject, object and relationship. Return the result as JSON without any additional text or explanation, and ensure it is enclosed within ```json ``` markers.\n" +
                "\n" +
                "sentence: " + sentence);
        List<String> keywords = getKeywords(answer);
        return keywords;
    }

    public   List<String> getKeywords(String input) {
        Pattern pattern = Pattern.compile("```json\\s*(\\{.*?\\})\\s*```", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            String jsonString = matcher.group(1);

            JsonReader jsonReader = Json.createReader(new StringReader(jsonString));
            JsonObject jsonObject = jsonReader.readObject();
            jsonReader.close();

            List<String> valuesList = new ArrayList<>();
            for (String key : jsonObject.keySet()) {
                JsonArray jsonArray = jsonObject.getJsonArray(key);
                for (int i = 0; i < jsonArray.size(); i++) {
                    valuesList.add(jsonArray.getString(i));
                }
            }
            return valuesList;
        } else {
            return null;
        }
    }

}
