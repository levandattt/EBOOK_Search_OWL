plugins {
	id("java")
	id("org.springframework.boot") version "2.7.16"
	id("io.spring.dependency-management") version "1.1.6"
}

configurations {
	compileOnly {
		extendsFrom(configurations.getByName("annotationProcessor"))
	}
}

repositories {
	mavenLocal()
	mavenCentral()
	maven {
		url = uri("https://packages.confluent.io/maven/")
	}
	// Add GitHub Packages repository
	maven {
		url = uri("https://maven.pkg.github.com/tqchu/SharedProto")
		credentials {
			username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
			password = project.findProperty("gpr.token") as String? ?: System.getenv("GITHUB_TOKEN")
		}
	}
}

dependencies {
	implementation(project(":Common"))
	implementation("org.springdoc:springdoc-openapi-ui:1.7.0")
	// https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-jpa
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	implementation("org.projectlombok:lombok-mapstruct-binding:0.1.0")
	implementation("org.mapstruct:mapstruct:1.5.5.Final")
	annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")
}

tasks.named<Test>("test") {
	useJUnitPlatform()
}
