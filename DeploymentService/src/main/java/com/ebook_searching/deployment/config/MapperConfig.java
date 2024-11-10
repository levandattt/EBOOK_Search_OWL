package com.ebook_searching.deployment.config;

import org.ebook_searching.common.mapper.DateMapper;
import org.ebook_searching.common.mapper.StringValueMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class MapperConfig {
    @Bean
    public DateMapper dateMapper() {
        return new DateMapper();
    }

    @Bean
    public StringValueMapper stringValueMapper() {
        return new StringValueMapper();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
