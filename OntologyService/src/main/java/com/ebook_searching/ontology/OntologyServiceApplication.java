package com.ebook_searching.ontology;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OntologyServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OntologyServiceApplication.class, args);
	}

//	@Bean
//	public NewTopic topic() {
//		return TopicBuilder.name("topic1")
//				.partitions(10)
//				.replicas(1)
//				.build();
//	}
//
//	@KafkaListener(id = "myId", topics = "topic1")
//	public void listen(String in) {
//		System.out.println(in);
//	}
}
