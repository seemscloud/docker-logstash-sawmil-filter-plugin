FROM debian:bullseye-slim

ARG APP_URL="https://artifacts.elastic.co/downloads/logstash"
ARG APP_VERSION="7.17.2"

ARG DEBIAN_FRONTEND=noninteractive

ENV APP_DIR="/root"
ENV PATH="${APP_DIR}/bin:${PATH}"

SHELL ["/bin/bash", "-c"]

RUN apt-get update && \
    apt-get install wget -y

WORKDIR /tmp

RUN wget "${APP_URL}/logstash-${APP_VERSION}-linux-x86_64.tar.gz" -O logstash.tar.gz && \
    tar -xf logstash.tar.gz -C "${APP_DIR}" --strip-components=1 && \
    mkdir -p "${APP_DIR}/certs" && \
    mkdir -p "${APP_DIR}/config/pipelines" && \
    rm -f logstash.tar.gz

WORKDIR "${APP_DIR}"

ENTRYPOINT [ "/bin/bash", "entrypoint.sh" ]