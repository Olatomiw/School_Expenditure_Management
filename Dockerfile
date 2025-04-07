FROM openjdk:17-jdk
LABEL authors="RLS"

WORKDIR /app
EXPOSE 8085
#COPY target/
CMD ["java", "jar"]