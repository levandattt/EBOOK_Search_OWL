plugins {
	id("java")
	`maven-publish`
	id("org.springframework.boot") version "2.7.16"
	id("io.spring.dependency-management") version "1.1.6"
}

java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(17))
	}
}

group = "org.ebook_searching"
version = "0.0.8"

publishing {
	publications {
		create<MavenPublication>("qdcSharedUtilities") {
			from(components["java"])

			artifactId = "common"

			pom {
				name.set("Shared utilities")
				description.set("Shared code for Ebook searching")
				licenses {
					license {
						name.set("The Apache License, Version 2.0")
						url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
					}
				}
				developers {
					developer {
						id.set("chu")
						name.set("Truong Quang Chu")
						email.set("truongquangchu.tqc@gmail.com")
					}
					developer {
						id.set("dat")
						name.set("Le Van Dat")
						email.set("lvd.levandat@gmail.com")
					}
				}
			}
		}
	}

	repositories {
		maven {
			name = "GitHubPackages"
			url = uri("https://maven.pkg.github.com/levandattt/EBOOK_Search_OWL")
			credentials {
				username = project.findProperty("gpr.user") as String? ?: System.getenv("GPR_USER")
				password = project.findProperty("gpr.token") as String? ?: System.getenv("GPR_TOKEN")
			}
		}
	}
}

// Disable the `bootJar` task, as this module is not an executable Spring Boot application
tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
	enabled = false
}

// Ensure the regular JAR task is enabled
tasks.getByName<Jar>("jar") {
	enabled = true
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
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.apache.kafka:kafka-streams")
	implementation("com.google.protobuf:protobuf-java:3.21.1")
	implementation("org.springframework.kafka:spring-kafka")
	implementation("com.google.protobuf:protobuf-java:3.23.4")
	implementation("javax.xml.bind:jaxb-api:2.3.1")
	implementation("com.fasterxml.jackson.core:jackson-databind")
	implementation("com.fasterxml.jackson.core:jackson-core")
	implementation("com.fasterxml.jackson.core:jackson-annotations")
// https://mvnrepository.com/artifact/io.confluent/kafka-protobuf-serializer
	implementation("io.confluent:kafka-protobuf-serializer:6.2.7")
	implementation("org.ebook_searching:proto:0.0.8")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.kafka:spring-kafka-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	compileOnly("org.projectlombok:lombok")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	implementation("org.projectlombok:lombok-mapstruct-binding:0.1.0")
	implementation("org.mapstruct:mapstruct:1.5.5.Final")
	annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")
}

configurations {
	compileOnly {
		extendsFrom(configurations.getByName("annotationProcessor"))
	}
}
