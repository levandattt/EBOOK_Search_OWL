plugins {
    java
    `java-library`
    `maven-publish`
    signing
    id("io.spring.dependency-management") version "1.1.6"
}

group = "org.ebook_searching"
version = "0.0.15"

publishing {
    publications {
        create<MavenPublication>("qdcSharedProto") {
            from(components["java"])

            artifactId = "proto"

            pom {
                name.set("Shared Proto")
                description.set("A mono shared proto definition for QDC")
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

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.protobuf:protobuf-java:3.23.4")
}

tasks.javadoc {
    if (JavaVersion.current().isJava9Compatible) {
        (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
