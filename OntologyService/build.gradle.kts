plugins {
	java
	id("io.spring.dependency-management") version "1.1.6"
	id("com.google.protobuf") version "0.8.13"
	id("org.springframework.boot") version "2.7.16"
}

// Common repositories across all projects
repositories {
	mavenLocal()
	mavenCentral()
	maven {
		url = uri("https://packages.confluent.io/maven/")
	}
	maven {
		url = uri("https://maven.pkg.github.com/levandattt/EBOOK_Search_OWL")
		credentials {
			username = project.findProperty("gpr.user") as String? ?: System.getenv("GPR_USER")
			password = project.findProperty("gpr.token") as String? ?: System.getenv("GPR_TOKEN")
		}
	}
}

dependencies {
	 implementation("org.ebook_searching:common:0.0.8")
	 implementation("org.ebook_searching:proto:0.0.15")
	implementation("com.google.protobuf:protobuf-java:3.21.1")
	implementation("org.apache.jena:jena-tdb:4.10.0")
	implementation("org.apache.jena:jena-arq:4.10.0")
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.apache.kafka:kafka-streams")
	implementation("org.springframework.kafka:spring-kafka")
	implementation("com.google.protobuf:protobuf-java:3.23.4")
	implementation("javax.xml.bind:jaxb-api:2.3.1")
	implementation("com.fasterxml.jackson.core:jackson-databind")
	implementation("com.fasterxml.jackson.core:jackson-core")
	implementation("com.fasterxml.jackson.core:jackson-annotations")
// https://mvnrepository.com/artifact/io.confluent/kafka-protobuf-serializer
	implementation("io.confluent:kafka-protobuf-serializer:6.2.7")
//	implementation("org.ebook_searching:proto:0.0.5")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.kafka:spring-kafka-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	implementation("org.springdoc:springdoc-openapi-ui:1.7.0")
	compileOnly("org.projectlombok:lombok")
//	developmentOnly("org.springframework.boot:spring-boot-devtools:2.7.16")

	implementation("org.apache.opennlp:opennlp-tools:2.4.0")

	// https://mvnrepository.com/artifact/com.azure/azure-ai-inference
	implementation("com.azure:azure-ai-inference:1.0.0-beta.2")


}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<JavaCompile> {
	options.isIncremental = true
	options.compilerArgs.add("-Amapstruct.suppressGeneratorTimestamp=true")
}
