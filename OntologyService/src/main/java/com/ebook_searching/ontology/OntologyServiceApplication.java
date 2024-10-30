package com.ebook_searching.ontology;

import com.ebook_searching.ontology.migrations.MigrationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class OntologyServiceApplication implements CommandLineRunner {
	@Autowired
	private MigrationManager migrationManager;

	public static void main(String[] args) {
		SpringApplication.run(OntologyServiceApplication.class, args);
	}

	@Override
	public void run(String... args) {
		try {
			migrationManager.runMigrations();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
