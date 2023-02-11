FROM openjdk:17
ADD target/crypto-recommendation-0.0.1-SNAPSHOT.jar crypto-recommendation.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "crypto-recommendation.jar"]