FROM openjdk:11-jre-slim

RUN apt-get update && apt-get install curl -y
RUN apt-get update && apt-get install -y jq
RUN  apt-get update \
  && apt-get install -y wget \
  && rm -rf /var/lib/apt/lists/*

WORKDIR /user/share/docker

ADD target/selenium-docker.jar  		selenium-docker.jar
ADD target/selenium-docker-tests.jar 	selenium-docker-tests.jar
ADD target/libs							libs

ADD test-output                         test-output
ADD testng.xml 							testng.xml

ADD healthcheck.sh healthcheck.sh

ENTRYPOINT sh healthcheck.sh