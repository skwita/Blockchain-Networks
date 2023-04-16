FROM maven:3-amazoncorretto-11 as builder
WORKDIR /app
COPY . .
RUN mvn verify package
FROM amazoncorretto:11.0.18-al2023
COPY --from=builder /app/target/blockchain-1.0-SNAPSHOT-jar-with-dependencies.jar blockchain.jar
ENTRYPOINT ["java", "-jar", "blockchain.jar"]