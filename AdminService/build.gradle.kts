plugins {
    java
    id("io.spring.dependency-management") version "1.1.6"
    id("com.google.protobuf") version "0.8.13"
    id("org.springframework.boot") version "2.7.16"
}

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
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.apache.kafka:kafka-streams")
    implementation("org.springframework.kafka:spring-kafka")
    implementation("com.google.protobuf:protobuf-java:3.23.4")
    implementation("javax.xml.bind:jaxb-api:2.3.1")
    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("com.fasterxml.jackson.core:jackson-core")
    implementation("com.fasterxml.jackson.core:jackson-annotations")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    // https://mvnrepository.com/artifact/io.confluent/kafka-protobuf-serializer
    implementation("io.confluent:kafka-protobuf-serializer:6.2.7")
    implementation("org.springdoc:springdoc-openapi-ui:1.7.0")
//    implementation("org.ebook_searching:proto:0.0.7")
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-mysql")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    // MySQL JDBC Driver
    implementation("mysql:mysql-connector-java:8.0.33")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.kafka:spring-kafka-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("com.google.protobuf:protobuf-java:3.21.1")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    implementation("org.projectlombok:lombok-mapstruct-binding:0.1.0")
    implementation("org.mapstruct:mapstruct:1.5.5.Final")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")
    // https://mvnrepository.com/artifact/no.entur.mapstruct.spi/protobuf-spi-impl
    implementation("no.entur.mapstruct.spi:protobuf-spi-impl:1.45")
}
