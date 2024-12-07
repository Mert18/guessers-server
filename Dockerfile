FROM eclipse-temurin:17

COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN ./mvnw dependency:resolve

COPY src src
RUN ./mvnw package

FROM eclipse-temurin:17
WORKDIR guessers
COPY target/*.jar guessers.jar

ENV SPRING_PROFILES_ACTIVE=prod

ENTRYPOINT ["java","-jar","guessers.jar"]
EXPOSE 8080