FROM debian:bullseye-slim

ARG APP_USER="logstash"
ARG APP_GROUP="${APP_USER}"
ARG APP_UID="1000"
ARG APP_GID="${APP_UID}"
ARG APP_URL="https://artifacts.elastic.co/downloads/logstash"
ARG APP_VERSION="7.17.2"

ARG DEBIAN_FRONTEND=noninteractive

ENV APP_DIR="/app"
ENV PATH="${APP_DIR}/bin:${PATH}"

SHELL ["/bin/bash", "-c"]

RUN apt-get update && \
    apt-get install wget openssl curl -y

RUN groupadd -g "${APP_GID}" "${APP_GROUP}" && \
    useradd -u "${APP_UID}" -g "${APP_GID}" -s /bin/bash -m -d "${APP_DIR}" "${APP_USER}"

WORKDIR /tmp

RUN wget "${APP_URL}/logstash-${APP_VERSION}-linux-x86_64.tar.gz" -O logstash.tar.gz && \
    tar -xvf logstash.tar.gz -C "${APP_DIR}" --strip-components=1 && \
    mkdir -p "${APP_DIR}/certs" && \
    mkdir -p "${APP_DIR}/config/pipelines" && \
    rm -f logstash.tar.gz

WORKDIR "${APP_DIR}"

RUN chown --recursive "${APP_USER}":"${APP_USER}" "${APP_DIR}"

# USER "${APP_USER}"

ENTRYPOINT [ "/bin/bash", "entrypoint.sh" ]