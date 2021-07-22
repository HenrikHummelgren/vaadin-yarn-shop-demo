FROM openjdk:11
WORKDIR /
ADD target/yarn-shop-1.0-SNAPSHOT.jar yarn-shop-app.jar
RUN useradd -m myuser
USER myuser
EXPOSE 8080
CMD java -jar -Dspring.profiles.active=prod yarn-shop-app.jar
