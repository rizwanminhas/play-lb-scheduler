FROM openjdk:13.0.2-jdk-oraclelinux7

RUN yum install -y unzip

WORKDIR /rizwan

COPY ./target/universal/play-lb-scheduler.zip /rizwan

RUN unzip /rizwan/play-lb-scheduler.zip
