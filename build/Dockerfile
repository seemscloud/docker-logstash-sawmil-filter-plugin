FROM openjdk:11-jdk-bullseye

ARG APP_URL="https://github.com/elastic/logstash.git"
ARG APP_VERSION="7.17"

ARG DEBIAN_FRONTEND=noninteractive

ENV APP_DIR="/root"

SHELL ["/bin/bash", "-c"]

RUN apt-get update && \
    apt-get install git -y

WORKDIR "${APP_DIR}"

RUN git clone --branch "${APP_VERSION}" --single-branch "${APP_URL}" logstash

RUN echo "org.gradle.parallel=true" >> logstash/gradle.properties && \
    echo "org.gradle.caching=true=true" >> logstash/gradle.properties && \
    echo "org.gradle.jvmargs=-Xmx4096m" >> logstash/gradle.properties

RUN cd logstash && \
    ./gradlew assemble

COPY files/configuration plugin
COPY files/source plugin

RUN cd plugin && \
    chmod +x gradlew && \
    ./gradlew gem

ENTRYPOINT [ "/bin/bash", "entrypoint.sh" ]