FROM maven:3.8.1-jdk-11 as maven
WORKDIR /app
COPY pom.xml /app/pom.xml
RUN mvn dependency:go-offline
COPY src /app/src/
RUN mvn package -DskipTests

FROM openjdk:11-jre
VOLUME /deployment
COPY --from=maven /app/target/review-it-backend.jar /deployment/review-it-backend.jar
EXPOSE 8083
ENTRYPOINT ["java","-jar","/deployment/review-it-backend.jar"]



