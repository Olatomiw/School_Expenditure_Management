FROM openjdk:17-jdk

LABEL authors="RLS"

WORKDIR /app

COPY target/School_Expenditure_Management-0.0.1-SNAPSHOT.jar /app/exp.jar
#COPY target/

CMD ["java", "-jar", "exp.jar"]

EXPOSE 8085

ENV SPRING_PROFILES_ACTIVE=docker
