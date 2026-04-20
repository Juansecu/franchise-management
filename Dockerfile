FROM amazoncorretto:21-alpine AS build
WORKDIR /app
COPY . .
RUN chmod +x ./mvnw
RUN ./mvnw clean package -DskipTests

FROM amazoncorretto:21-alpine AS production
ENV PORT=8080
VOLUME /tmp
WORKDIR /app
COPY --from=build /app/target/*.jar ./app.jar
EXPOSE $PORT
CMD ["java", "-jar", "app.jar"]
