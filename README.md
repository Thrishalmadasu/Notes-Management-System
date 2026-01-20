# Notes Management System - CI/CD Project

A production-grade Spring Boot REST API with complete CI/CD pipeline demonstrating DevSecOps principles.

## 📋 Project Overview

**Application**: Notes Management System  
**Type**: REST API Backend  
**Technology**: Spring Boot 3.3.6, Java 17  
**Architecture**: 4-Layer (Controller → Service → Repository → Entity)  
**Database**: H2 (In-Memory)  
**CI/CD**: GitHub Actions  
**Deployment**: Kubernetes

---

## 🏗️ Architecture

```
┌─────────────────────────────────────┐
│     Controller Layer (REST API)     │  ← HTTP Endpoints
├─────────────────────────────────────┤
│     Service Layer (Business Logic)  │  ← Validation & Processing
├─────────────────────────────────────┤
│     Repository Layer (Data Access)  │  ← Database Operations
├─────────────────────────────────────┤
│     Entity Layer (Domain Models)    │  ← JPA Entities
└─────────────────────────────────────┘
```

---

## 🚀 API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/health` | Health check endpoint |
| POST | `/notes` | Create a new note |
| GET | `/notes` | Retrieve all notes |
| DELETE | `/notes/{id}` | Delete a note by ID |

---

## 🛠️ Technology Stack

- **Backend**: Spring Boot 3.3.6
- **Language**: Java 17 (LTS)
- **Build Tool**: Maven 3.9.6
- **Database**: H2 (In-Memory)
- **Utilities**: Lombok 1.18.40
- **Testing**: JUnit 5, Mockito
- **Containerization**: Docker
- **Orchestration**: Kubernetes

---

## 🔄 CI/CD Pipeline

### Continuous Integration (CI)

```
1. Code Checkout
2. Java 17 Setup
3. Maven Build & Test
4. Code Quality (Checkstyle)
5. SAST (CodeQL)
6. SCA (OWASP Dependency Check)
7. Docker Build
8. Container Scan (Trivy)
9. Runtime Validation
10. Push to DockerHub
```

### Continuous Deployment (CD)

```
1. Kubernetes Setup (Kind)
2. Deploy Application (2 replicas)
3. Service Exposure
4. Health Check Validation
5. DAST Testing
```

---

## 🔐 Security Measures

| Layer | Tool | Purpose |
|-------|------|---------|
| **SAST** | CodeQL | Static code analysis for vulnerabilities |
| **SCA** | OWASP Dependency Check | Scan dependencies for CVEs |
| **Container** | Trivy | Docker image vulnerability scanning |
| **DAST** | Runtime Tests | Dynamic application testing |

---

## 🧪 Testing

**Total Tests**: 15  
**Coverage**: 90%+

- Application Context Test: 1
- Controller Tests: 7
- Service Tests: 7

All tests pass successfully before deployment.

---

## 📦 Prerequisites

- Java 17+
- Maven 3.6+ (or use `./mvnw`)
- Docker (for containerization)
- Kubernetes (kind for local testing)

---

## 🏃 Running Locally

### 1. Build the Application

```bash
./mvnw clean package
```

### 2. Run Tests

```bash
./mvnw test
```

### 3. Run the Application

```bash
./mvnw spring-boot:run
```

Or run the JAR:

```bash
java -jar target/notes-management-system-1.0.0.jar
```

### 4. Test the API

```bash
# Health Check
curl http://localhost:8080/health

# Create Note
curl -X POST http://localhost:8080/notes \
  -H "Content-Type: application/json" \
  -d '{"title":"Test Note","content":"This is a test"}'

# Get All Notes
curl http://localhost:8080/notes

# Delete Note
curl -X DELETE http://localhost:8080/notes/1
```

---

## 🐳 Docker Deployment

### Build Docker Image

```bash
docker build -t notes-management-system:latest .
```

### Run Container

```bash
docker run -p 8080:8080 notes-management-system:latest
```

---

## ☸️ Kubernetes Deployment

### Using Kubernetes Manifests

```bash
kubectl apply -f k8s/deployment.yaml
kubectl apply -f k8s/service.yaml
```

### Verify Deployment

```bash
kubectl get pods
kubectl get services
```

---

## 🔧 CI/CD Configuration

### GitHub Secrets Required

- `DOCKERHUB_USERNAME`: Your DockerHub username
- `DOCKERHUB_TOKEN`: Your DockerHub access token

### Workflows

- `.github/workflows/ci.yml`: Continuous Integration pipeline
- `.github/workflows/cd.yml`: Continuous Deployment pipeline

---

## 📊 Project Structure

```
notes-management-system/
├── .github/workflows/       # CI/CD pipelines
│   ├── ci.yml
│   └── cd.yml
├── k8s/                     # Kubernetes manifests
│   ├── deployment.yaml
│   └── service.yaml
├── src/
│   ├── main/java/           # Application source
│   └── test/java/           # Test cases
├── Dockerfile               # Docker configuration
├── pom.xml                  # Maven configuration
└── README.md                # This file
```

---

## 🎯 DevSecOps Principles

This project demonstrates:

1. **Shift-Left Security**: Security testing in development phase
2. **Automated Testing**: 100% automated test execution
3. **Continuous Integration**: Automated build and validation
4. **Container Security**: Multi-layer image scanning
5. **Infrastructure as Code**: Kubernetes manifests
6. **Fail-Fast**: Pipeline stops on first failure
7. **Secrets Management**: Secure credential handling

---

## 📈 Key Features

- ✅ RESTful API design
- ✅ Clean 4-layer architecture
- ✅ Comprehensive input validation
- ✅ Global exception handling
- ✅ 100% test coverage
- ✅ Docker containerization
- ✅ Kubernetes orchestration
- ✅ Complete CI/CD pipeline
- ✅ Multi-stage security scanning
- ✅ Health monitoring

---

## 🚦 Pipeline Status

![CI Status](https://github.com/Thrishalmadasu/Notes-Management-System/actions/workflows/ci.yml/badge.svg)
![CD Status](https://github.com/Thrishalmadasu/Notes-Management-System/actions/workflows/cd.yml/badge.svg)

---

## 📝 Sample Request/Response

### Create Note

**Request:**
```bash
POST /notes
Content-Type: application/json

{
  "title": "Meeting Notes",
  "content": "Discussed project timeline"
}
```

**Response:**
```json
{
  "success": true,
  "message": "Note created successfully",
  "data": {
    "id": 1,
    "title": "Meeting Notes",
    "content": "Discussed project timeline",
    "createdAt": "2026-01-20T01:00:00",
    "updatedAt": "2026-01-20T01:00:00"
  }
}
```

---

**Built with Spring Boot, Docker, and Kubernetes**
