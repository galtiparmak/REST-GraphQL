FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY target/graphql.jar /app/graphql.jar
EXPOSE 8080
CMD ["java", "-jar", "/app/graphql.jar"]