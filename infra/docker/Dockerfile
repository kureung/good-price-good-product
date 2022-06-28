FROM openjdk:11-jdk
COPY build/libs/gpgp-0.0.1-SNAPSHOT.jar gpgp.jar

ARG SPRING_PROFILE=local

EXPOSE 8080

CMD ["java", "-jar", "gpgp.jar"]