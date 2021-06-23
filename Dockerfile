#
# Build stage
#
FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

#
# Package stage
#
FROM openjdk:11.0.10-jdk-oracle
COPY --from=build /home/app/target/taskmanager-0.0.1-SNAPSHOT.jar app.jar
ENV JAVA_OPTS="-XX:-TieredCompilation -Xmx768M -XX:MaxMetaspaceSize=192M -XX:CompressedClassSpaceSize=32M -XX:ReservedCodeCacheSize=29M -Xss1024K"
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /app.jar"]
