FROM openjdk:11-jdk-bullseye

ARG APP_URL="https://github.com/elastic/logstash.git"
ARG APP_VERSION="7.17"

ARG DEBIAN_FRONTEND=noninteractive

ENV APP_DIR="/root"

SHELL ["/bin/bash", "-c"]

RUN apt-get update && \
    apt-get install git -y

WORKDIR "${APP_DIR}"

COPY ./plugin plugin

RUN git clone --branch "${APP_VERSION}" --single-branch "${APP_URL}" logstash

RUN cd logstash && \
    ./gradlew assemble

RUN cd plugin && \
    chmod +x gradlew && \
    ./gradlew gem

ENTRYPOINT [ "/bin/bash", "entrypoint.sh" ]