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
	implementation("org.springdoc:springdoc-openapi-ui:1.7.0")
}

tasks.named<Test>("test") {
	useJUnitPlatform()
}
