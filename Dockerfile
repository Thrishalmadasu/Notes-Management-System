# Use Eclipse Temurin JDK 17 (OpenJDK)
FROM eclipse-temurin:17-jdk-alpine AS build

# Set working directory
WORKDIR /app

# Copy Maven wrapper and pom.xml
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

# Download dependencies (cached layer)
RUN ./mvnw dependency:go-offline || true

# Copy source code
COPY src ./src

# Build the application
RUN ./mvnw clean package -DskipTests

# Runtime stage - Using distroless for minimal attack surface
FROM gcr.io/distroless/java17-debian12

WORKDIR /app

# Copy the JAR from build stage
COPY --from=build /app/target/notes-management-system-1.0.0.jar app.jar

# Expose port
EXPOSE 8080

# Note: Distroless doesn't support HEALTHCHECK with wget/curl
# Health checks should be done at orchestration level (K8s liveness/readiness probes)

# Run the application as non-root user (distroless defaults to non-root)
ENTRYPOINT ["java", "-jar", "app.jar"]
