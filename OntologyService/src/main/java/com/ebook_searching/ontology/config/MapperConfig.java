package com.ebook_searching.ontology.config;

import org.ebook_searching.common.mapper.DateMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    @Bean
    public DateMapper dateMapper() {
        return new DateMapper();
    }
}
