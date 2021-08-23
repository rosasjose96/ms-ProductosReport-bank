FROM openjdk:8
VOLUME /temp
EXPOSE 8083
ADD ./target/ms-ProductosReport-bank-0.0.1-SNAPSHOT.jar productreport-service.jar
ENTRYPOINT ["java","-jar","/productreport-service.jar"]