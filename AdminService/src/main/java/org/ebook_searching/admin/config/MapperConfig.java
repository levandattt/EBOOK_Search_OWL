package org.ebook_searching.admin.config;

import org.ebook_searching.common.mapper.DateMapper;
import org.ebook_searching.common.mapper.StringValueMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
}
