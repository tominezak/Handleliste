# --- Build ---
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -q -DskipTests dependency:go-offline
COPY src ./src
RUN mvn -q -DskipTests package

# --- Run ---
FROM eclipse-temurin:17-jre
WORKDIR /app
# NB: kopierer én JAR og kaller den app.jar
COPY --from=build /app/target/*-SNAPSHOT.jar app.jar
# Hvis du ikke bruker -SNAPSHOT, bruk *.jar:
# COPY --from=build /app/target/*.jar app.jar

ENV PORT=8080
ENV JAVA_OPTS="-Xms256m -Xmx512m"
EXPOSE 8080
# Ekstra logging ved oppstart for feilsøking
CMD sh -c 'java -version && echo "PORT=$PORT" && ls -l && java $JAVA_OPTS -jar app.jar'