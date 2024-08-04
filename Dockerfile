FROM openjdk:19
EXPOSE 8080
ADD target/XM-0.0.1-SNAPSHOT.war springboot-k8s-xm.jar
ENTRYPOINT ["java","-jar","/springboot-k8s-xm.jar"]
