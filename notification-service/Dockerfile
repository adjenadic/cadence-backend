FROM maven:3.9.6 AS builder

ENV HOME=/usr/app
RUN mkdir -p $HOME
WORKDIR $HOME

ADD . $HOME

RUN --mount=type=cache,target=/root/.m2 mvn -DfinalName=app -Dmaven.test.skip -f $HOME/pom.xml clean package

FROM openjdk:17

COPY --from=builder /usr/app/target/*.jar /app.jar

EXPOSE 8002
ENTRYPOINT ["java", "-jar", "/app.jar"]
