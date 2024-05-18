# Useful Commands

## Cluster

kubectl get nodes

## Pod

* kubectl run my-pod --image=nginx --port=80
* kubectl get pods
* kubectl describe pod my-pod
* kubectl logs my-pod
* kubectl exec -it my-pod -- bash
* kubectl port-forward my-pod 7777:80
* kubectl delete pod my-pod

## Deployment

* kubectl create deployment my-deployment --image=nginx --replicas=3
* kubectl set image deployment my-deployment nginx=nginx:1 --record


* kubectl scale deployment my-deployment --replicas=4
* kubectl autoscale deployment my-deployment --min=2 --max=10 --cpu-percent=90
* kubectl get horizontalpodautoscalers.autoscaling


* kubectl rollout history deployment my-deployment
* kubectl rollout status deployment my-deployment
* kubectl rollout undo deployment my-deployment --to-revision=2
* kubectl rollout restart deployment my-deployment

## Services

### Types

* ClusterIP (default, internal IP)
* NodePort (a port on all nodes)
* LoadBalancer (external IP and port)
* ExternalName (DNS)


* kubectl expose deployment nginx --type=ClusterIP --port=80
* kubectl expose deployment nginx --type=NodePort --port=80
* kubectl expose deployment nginx --type=LoadBalancer --port=80
