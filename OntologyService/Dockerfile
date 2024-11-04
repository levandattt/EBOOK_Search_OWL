# Use an OpenJDK base image with Gradle installed
FROM gradle:7.5.1-jdk17 AS builder

# Set the working directory inside the container
WORKDIR /app
# Accept build arguments for GPR credentials
ARG GPR_USER
ARG GPR_TOKEN

ENV GPR_USER=$GPR_USER
ENV GPR_TOKEN=$GPR_TOKEN
# Copy the Gradle build files for this specific service
COPY build.gradle.kts ./
COPY src ./src
# Install netcat and dnsutils for networking tools
# Build the application (you may want to skip tests if they're not essential for building)
RUN gradle clean build -x test

# Start a new image for the runtime
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the builder stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Expose the application's port (update this if different services use different ports)
EXPOSE 8084

# Define environment variables for Kafka, MySQL, or any other dependencies if needed

# Run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]