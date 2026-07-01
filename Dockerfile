FROM eclipse-temurin:25

COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN ./mvnw dependency:resolve

COPY src src
RUN ./mvnw package

FROM eclipse-temurin:25
WORKDIR guessers
COPY target/*.jar guessers.jar

ENV SPRING_PROFILES_ACTIVE=prod
ENV TZ=Europe/Istanbul

ENTRYPOINT ["java","-jar","guessers.jar"]
EXPOSE 8080