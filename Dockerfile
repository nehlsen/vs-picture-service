FROM amazoncorretto:17-alpine AS build

WORKDIR /app
COPY ./pom.xml ./mvnw /app/
COPY ./.mvn /app/.mvn
RUN cd /app && /app/mvnw dependency:resolve

ADD . /app
RUN cd /app && /app/mvnw package

FROM amazoncorretto:17-alpine

WORKDIR /app
COPY --from=build /app/target/PictureService-0.0.1-SNAPSHOT.jar /app/
CMD ["/usr/bin/java", "-jar", "/app/PictureService-0.0.1-SNAPSHOT.jar"]
