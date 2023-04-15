FROM maven:3-amazoncorretto-11 as builder

ADD ./pom.xml pom.xml
ADD ./src src/

RUN mvn -f pom.xml clean package

FROM openjdk:11.0-jre-slim
COPY --from=builder target/blockchain-1.0-SNAPSHOT.jar blockchain-1.0-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "blockchain-1.0-SNAPSHOT.jar"]