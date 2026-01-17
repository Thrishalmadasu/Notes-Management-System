# Kubernetes Deployment Guide

## 📋 Prerequisites

- Kubernetes cluster (minikube, kind, or cloud provider)
- kubectl configured
- Docker image pushed to DockerHub

## 🚀 Quick Deploy

### 1. Update Image Name

Edit `deployment.yaml` and replace `<DOCKERHUB_USERNAME>` with your DockerHub username:

```yaml
image: YOUR_DOCKERHUB_USERNAME/notes-management-system:latest
```

### 2. Apply Manifests

```bash
kubectl apply -f deployment.yaml
kubectl apply -f service.yaml
```

### 3. Verify Deployment

```bash
# Check deployment status
kubectl get deployments

# Check pods
kubectl get pods

# Check service
kubectl get services
```

### 4. Access the Application

#### If using NodePort:

```bash
# Get the NodePort
kubectl get svc notes-api-service

# Access the application
curl http://<NODE_IP>:<NODE_PORT>/health
```

#### If using Port Forward:

```bash
kubectl port-forward service/notes-api-service 8080:8080
curl http://localhost:8080/health
```

## 🔍 Monitoring

### View Logs

```bash
# Get pod name
kubectl get pods

# View logs
kubectl logs <POD_NAME>

# Follow logs
kubectl logs -f <POD_NAME>
```

### Describe Resources

```bash
# Describe deployment
kubectl describe deployment notes-api

# Describe pod
kubectl describe pod <POD_NAME>

# Describe service
kubectl describe service notes-api-service
```

## 📊 Scaling

### Scale Up

```bash
kubectl scale deployment notes-api --replicas=3
```

### Scale Down

```bash
kubectl scale deployment notes-api --replicas=1
```

## 🔄 Update Deployment

### Rolling Update

```bash
kubectl set image deployment/notes-api \
  notes-api=YOUR_DOCKERHUB_USERNAME/notes-management-system:v2
```

### Rollback

```bash
kubectl rollout undo deployment/notes-api
```

## 🧹 Cleanup

```bash
kubectl delete -f service.yaml
kubectl delete -f deployment.yaml
```

## 🔒 Production Considerations

1. **Use LoadBalancer** instead of NodePort
2. **Add Ingress** for external access
3. **Configure Resource Limits** properly
4. **Set up HPA** (Horizontal Pod Autoscaler)
5. **Use ConfigMaps** for configuration
6. **Use Secrets** for sensitive data
7. **Add Persistent Volumes** if using real database
8. **Configure Network Policies** for security
9. **Set up Monitoring** (Prometheus/Grafana)
10. **Configure Logging** (ELK/Loki)
