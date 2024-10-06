plugins {
    java
    id("io.spring.dependency-management") version "1.1.6"
}

extra["springCloudVersion"] = "2023.0.3"

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
            username = project.findProperty("gpr.user") as String
            password = project.findProperty("gpr.token") as String
        }
    }
}

dependencies {
    implementation("org.springframework.cloud:spring-cloud-starter-gateway") // Correct dependency for Spring Cloud Gateway using WebFlux
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}
