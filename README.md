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

curl --insecure https://192.168.99.100/hello/world
