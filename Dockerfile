FROM maven:3.9.5 AS build
COPy . .
RUN mvn clean package -DskipTests

FROM openjdk:21-jdk
COPY --from=build target/Champions.League.Zone-0.0.1-SNAPSHOT.jar champion.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","champion.jar"]
