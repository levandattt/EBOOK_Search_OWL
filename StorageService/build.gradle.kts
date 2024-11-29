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
        url = uri("https://maven.pkg.github.com/levandattt/EBOOK_Search_OWL")
        credentials {
            username = project.findProperty("gpr.user") as String? ?: System.getenv("GPR_USER")
            password = project.findProperty("gpr.token") as String? ?: System.getenv("GPR_TOKEN")
        }
    }
}

dependencies {
    implementation("org.ebook_searching:proto:0.0.16")
    implementation("org.springdoc:springdoc-openapi-ui:1.7.0")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    implementation("org.projectlombok:lombok-mapstruct-binding:0.1.0")
    implementation("org.mapstruct:mapstruct:1.5.5.Final")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")
    implementation("org.apache.kafka:kafka-streams")
    implementation("org.springframework.kafka:spring-kafka")
    implementation("io.confluent:kafka-protobuf-serializer:6.2.7")
    testImplementation("org.springframework.kafka:spring-kafka-test")
    implementation("org.flywaydb:flyway-mysql")
    implementation("mysql:mysql-connector-java:8.0.33")
    implementation("io.minio:minio:8.5.6")
    implementation("io.grpc:grpc-netty:1.56.0")
    implementation("io.grpc:grpc-protobuf:1.56.0")
    implementation("io.grpc:grpc-stub:1.56.0")
    implementation("com.google.protobuf:protobuf-java:3.23.0")
    implementation("org.springframework.boot:spring-boot-starter-web")
    // https://mvnrepository.com/artifact/net.devh/grpc-server-spring-boot-starter
    implementation("net.devh:grpc-server-spring-boot-starter:2.7.0.RELEASE")

}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

configurations.all {
    resolutionStrategy.eachDependency {
        if (requested.group == "io.grpc") {
            useVersion("1.56.0")
        }
    }
}

