FROM openjdk:11.0.4

ADD target/easify-backend.jar easify-backend.jar

EXPOSE 8090

ENTRYPOINT ["java", "-jar", "easify-backend.jar"]
