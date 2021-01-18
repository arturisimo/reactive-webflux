FROM openjdk:8-jdk-alpine

ADD target/quijote-reactive-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
CMD ["/bin/bash"]