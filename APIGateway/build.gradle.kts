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
       url = uri("https://maven.pkg.github.com/levandattt/EBOOK_Search_OWL")
        credentials {
            username = project.findProperty("gpr.user") as String? ?: System.getenv("GPR_USER")
            password = project.findProperty("gpr.token") as String? ?: System.getenv("GPR_TOKEN")
        }
    }
}

dependencies {
    // Spring Cloud Gateway for WebFlux
    // https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-gateway
    implementation("org.springframework.cloud:spring-cloud-starter-gateway")

    // SpringDoc OpenAPI for WebFlux
    implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:2.1.0")

    // Spring Boot WebFlux
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    // Ensure Reactor Netty 1.0.x for Spring Boot 2.7.x
    implementation("io.projectreactor.netty:reactor-netty-http:1.0.34") // Latest 1.0.x version
    implementation("io.projectreactor.netty:reactor-netty-core:1.0.34")


}


dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}
