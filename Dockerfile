# Use Maven for build stage
FROM maven:3.9.9-amazoncorretto-17 AS build

ARG MONGO_URI
ENV MONGO_URI=$MONGO_URI

ARG URL_UI
ENV URL_UI=$URL_UI

WORKDIR /app

# Copy pom.xml and download dependencies first for Docker cache efficiency
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .
RUN chmod +x ./mvnw
RUN ./mvnw dependency:go-offline

# Copy the rest of the source code
COPY src ./src

# Build the app
RUN ./mvnw clean package -DskipTests

# ---- Second stage: Use Amazon Corretto for final image
FROM amazoncorretto:17-alpine3.20

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
