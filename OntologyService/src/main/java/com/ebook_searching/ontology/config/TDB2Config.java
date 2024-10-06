package com.ebook_searching.ontology.config;

import org.apache.jena.query.Dataset;
import org.apache.jena.tdb.TDBFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TDB2Config {
    @Value("${ontology.tdb2.directory}")
    private String directory;

    @Bean
    public Dataset dataset() {
        return TDBFactory.createDataset(directory);
    }
}
