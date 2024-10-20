plugins {
	java
	id("io.spring.dependency-management") version "1.1.6"
	id("com.google.protobuf") version "0.8.13"
	id("org.springframework.boot") version "3.3.4"
}

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

// Common repositories across all projects
repositories {
	mavenLocal()
	mavenCentral()
	maven {
		url = uri("https://packages.confluent.io/maven/")
	}
	maven {
		url = uri("https://maven.pkg.github.com/tqchu/SharedProto")
		credentials {
			username = project.findProperty("gpr.user") as String? ?: System.getenv("GPR_USER")
			password = project.findProperty("gpr.token") as String? ?: System.getenv("GPR_TOKEN")
		}
	}
}

dependencies {
	implementation(project(":Common"))
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
	implementation("org.ebook_searching:proto:0.0.5")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.kafka:spring-kafka-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	compileOnly("org.projectlombok:lombok")
	implementation("org.springframework.boot:spring-boot-devtools:3.3.4")
}

tasks.withType<Test> {
	useJUnitPlatform()
}