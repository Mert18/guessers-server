FROM eclipse-temurin:17

COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN ./mvnw dependency:resolve

COPY src src
RUN ./mvnw package

FROM eclipse-temurin:17
WORKDIR unlucky
COPY target/*.jar unlucky.jar

ENTRYPOINT ["java","-jar","unlucky.jar"]
EXPOSE 8080