FROM openjdk:21-oracle

# Set metadata
LABEL authors="anaslahboub"
LABEL description="Inventory Service for Spring Boot Keycloak Integration"


# Create application directory
WORKDIR /app

# Create volume for temporar y files
VOLUME /tmp

# Copy the JAR file from Maven target directory
COPY target/*.jar app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]