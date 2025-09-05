# --- Build stage ---
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -q -DskipTests dependency:go-offline
COPY src ./src
RUN mvn -q -DskipTests package

# --- Run stage ---
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENV PORT=8080
ENV JAVA_OPTS="-Xms256m -Xmx512m"
EXPOSE 8080
# Spring Boot får port fra env, og vi setter moderat heap
CMD ["sh","-c","java $JAVA_OPTS -jar app.jar"]