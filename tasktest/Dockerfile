FROM maven:3.9.8-eclipse-temurin-17

WORKDIR /tasktest
COPY . /tasktest/
RUN mvn clean install
EXPOSE 8081
CMD mvn spring-boot:run
