plugins {
    java
    id("io.spring.dependency-management") version "1.1.6"
    id("com.google.protobuf") version "0.8.13"
    id("org.springframework.boot") version "3.3.4"
}

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
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.apache.kafka:kafka-streams")
    implementation("org.springframework.kafka:spring-kafka")
    implementation("com.google.protobuf:protobuf-java:3.23.4")
    implementation("javax.xml.bind:jaxb-api:2.3.1")
    implementation("org.mapstruct:mapstruct:1.5.0.Final")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.5.0.Final")
    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("com.fasterxml.jackson.core:jackson-core")
    implementation("com.fasterxml.jackson.core:jackson-annotations")
    // https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-jpa
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    // https://mvnrepository.com/artifact/io.confluent/kafka-protobuf-serializer
    implementation("io.confluent:kafka-protobuf-serializer:6.2.7")
    implementation("org.ebook_searching:proto:0.0.6")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor("org.projectlombok:lombok")
    // MySQL JDBC Driver
    implementation("mysql:mysql-connector-java:8.0.33")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.kafka:spring-kafka-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    compileOnly("org.projectlombok:lombok")
}
