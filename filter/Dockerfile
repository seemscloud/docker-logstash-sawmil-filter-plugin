FROM openjdk:11-jdk-bullseye

ARG APP_URL="https://github.com/elastic/logstash.git"
ARG APP_VERSION="7.17"

ARG DEBIAN_FRONTEND=noninteractive

ENV APP_DIR="/app"
ENV PATH="${APP_DIR}/bin:${PATH}"

ENV  LOGSTASH_CORE_PATH="${APP_DIR}"/logstash/logstash-core

SHELL ["/bin/bash", "-c"]

RUN apt-get update && \
    apt-get install wget openssl curl git -y

WORKDIR "${APP_DIR}"

RUN git clone --branch "${APP_VERSION}" --single-branch "${APP_URL}" logstash && \
    cd logstash && \
    ./gradlew assemble

COPY ./plugin plugin

RUN cd plugin && \
    chmod +x gradlew && \
    ./gradlew gem

ENTRYPOINT [ "/bin/bash", "entrypoint.sh" ]