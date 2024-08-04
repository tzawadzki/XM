# XM
XM Crypto Investment

# Installation

* build application
* adjust version of application id dockerfile and application-deployment.yaml
* create image, for example run from main project directory
  ```docker build -t springboot-k8s-xm:1.0.0 .```
* deploy image to kubernetes cluster
  ```kubectl apply -f kubernetes/application/application-deployment.yaml```
* create service
  ```kubectl apply -f kubernetes/application/application-service.yaml```

## REST Documentation

OpenAPI http://localhost:8090/v3/api-docs

Swagger: http://localhost:8090/swagger-ui/index.html#/

## TODO (because lack of time)

* more cases in tests, junit tests
* validation of uploaded file consistency
* adjust Spring Rest Data configuration or add endpoints manually to manage not allowed crypto list
* adjust documentation

## TODO in future

* add https
* 
