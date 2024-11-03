package com.ebook_searching.ontology.service.Impl;

import com.ebook_searching.ontology.service.OpenAIChatService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

import com.azure.ai.inference.ChatCompletionsClient;
import com.azure.ai.inference.ChatCompletionsClientBuilder;
import com.azure.ai.inference.models.ChatCompletions;
import com.azure.ai.inference.models.ChatCompletionsOptions;
import com.azure.ai.inference.models.ChatRequestMessage;
import com.azure.ai.inference.models.ChatRequestSystemMessage;
import com.azure.ai.inference.models.ChatRequestUserMessage;
import com.azure.core.credential.AzureKeyCredential;


@Service
public class OpenAIChatServiceImpl implements OpenAIChatService {
    @Override
    public String chat(String message) {
        // To authenticate with the model you will need to generate a personal access token (PAT) in your GitHub settings.
        // Create your PAT token by following instructions here: https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/managing-your-personal-access-tokens
        String key = System.getenv("GPR_TOKEN");
        String endpoint = "https://models.inference.ai.azure.com";
        String model = "gpt-4o-mini";

        ChatCompletionsClient client = new ChatCompletionsClientBuilder()
                .credential(new AzureKeyCredential(key))
                .endpoint(endpoint)
                .buildClient();

        List<ChatRequestMessage> chatMessages = Arrays.asList(
                new ChatRequestSystemMessage(""),
                new ChatRequestUserMessage(message)
        );

        ChatCompletionsOptions chatCompletionsOptions = new ChatCompletionsOptions(chatMessages);
        chatCompletionsOptions.setModel(model);

        ChatCompletions completions = client.complete(chatCompletionsOptions);

        System.out.printf("%s.%n", completions.getChoice().getMessage().getContent());
        return completions.getChoice().getMessage().getContent();
    }
}
