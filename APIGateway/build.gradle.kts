plugins {
    java
    id("io.spring.dependency-management") version "1.1.6"
    id("org.springframework.boot") version "2.7.16"
}

extra["springCloudVersion"] = "2021.0.8"

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
    implementation("org.springframework.cloud:spring-cloud-starter-gateway")
    implementation("org.springdoc:springdoc-openapi-webflux-ui:1.6.14") // Compatible version for Spring Boot 2.7.x
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("io.projectreactor.netty:reactor-netty-http:1.0.34") // Latest 1.0.x version
    implementation("io.projectreactor.netty:reactor-netty-core:1.0.34")
}


dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}
