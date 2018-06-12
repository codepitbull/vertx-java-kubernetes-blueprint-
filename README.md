minikube start --memory 4048 --cpus 4
minikube addons enable ingress

kubectl config use-context minikube
eval $(minikube docker-env)

mvn clean install dockerfile:build

# Create Hazelcast service
kubectl apply -f kubernetes/hazelcast-rbac.yaml
kubectl create -f kubernetes/hazelcast.yaml

# Create Deployment and scale
kubectl create -f kubernetes/deployment.yaml
kubectl scale --replicas=2 -f kubernetes/deployment.yaml
kubectl expose deployment/vertx-kubernetes

# Publish via Ingress
kubectl create -f kubernetes/ingress.yaml

# Update image
kubectl set image deployment/vertx-backend vertx-backend=codepitbull/vertx-java-kubernetes-backend:4

mvn clean install dockerfile:build

kubectl delete pods <pod> --grace-period=0 --force

kubectl exec -it shell-demo -- /bin/bash


curl --insecure https://192.168.99.100/hello/world
