# syntax=docker/dockerfile:1

# ---------- Build stage ----------
FROM eclipse-temurin:26-jdk AS build
WORKDIR /workspace

# Copy build files first so dependency downloads are cached
COPY mvnw pom.xml ./
COPY .mvn .mvn
RUN --mount=type=cache,target=/root/.m2 ./mvnw -q dependency:go-offline

# Build the app
COPY src src
RUN --mount=type=cache,target=/root/.m2 ./mvnw -q clean package -DskipTests

# Split the fat jar into layers for better caching
RUN java -Djarmode=tools -jar target/*.jar extract --layers --destination extracted

# ---------- Runtime stage ----------
FROM eclipse-temurin:26-jre AS runtime
WORKDIR /app

# Run as a non-root user
RUN groupadd --system app && useradd --system --gid app --no-create-home app
USER app

COPY --from=build /workspace/extracted/dependencies/ ./
COPY --from=build /workspace/extracted/spring-boot-loader/ ./
COPY --from=build /workspace/extracted/snapshot-dependencies/ ./
COPY --from=build /workspace/extracted/application/ ./

EXPOSE 8080

ENV JAVA_OPTS="-XX:MaxRAMPercentage=75 -XX:+ExitOnOutOfMemoryError"

ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar /app/*.jar"]