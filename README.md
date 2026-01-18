# Notes Management System

A simple Spring Boot REST API for managing notes with automated CI/CD pipeline.

## What it does

This is a basic notes application that lets you create, read, and delete notes through REST endpoints. It's built with Spring Boot and includes a complete CI/CD pipeline for deployment to Kubernetes.

## Tech stack

- Spring Boot 3.3.6 with Java 17
- H2 in-memory database
- Maven for builds
- Docker for containerization
- Kubernetes for deployment
- GitHub Actions for CI/CD

## API endpoints

- `GET /health` - Check if the app is running
- `POST /notes` - Create a note
- `GET /notes` - Get all notes
- `DELETE /notes/{id}` - Delete a note

## Requirements

- Java 17
- Maven (or use the included `./mvnw`)
- Docker (optional)
- kubectl and kind (for Kubernetes deployment)

## Running locally

Build and run:
```bash
./mvnw clean package
./mvnw spring-boot:run
```

Test the API:
```bash
curl http://localhost:8080/health
curl -X POST http://localhost:8080/notes -H "Content-Type: application/json" -d '{"title":"Test","content":"Hello world"}'
curl http://localhost:8080/notes
```

## Docker

Build image:
```bash
docker build -t notes-app .
```

Run container:
```bash
docker run -p 8080:8080 notes-app
```

## Kubernetes deployment

Deploy to cluster:
```bash
kubectl apply -f k8s/deployment.yaml
kubectl apply -f k8s/service.yaml
```

## CI/CD

The pipeline runs tests, security scans, builds a Docker image, and deploys to Kubernetes. You need these GitHub secrets:
- `DOCKERHUB_USERNAME`
- `DOCKERHUB_TOKEN`
