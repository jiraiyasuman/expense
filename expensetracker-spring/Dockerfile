FROM eclipse-temurin:17

LABEL maintainer="www.github.com/jiraiyasuman"

WORKDIR /app

COPY target/expensetracker-spring-0.0.1-SNAPSHOT.jar /app/expensetracker-spring.jar

ENTRYPOINT [ "java","-jar","expensetracker-spring.jar" ]
