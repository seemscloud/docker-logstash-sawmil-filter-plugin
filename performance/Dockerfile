FROM debian:bullseye-slim

ARG APP_DIR="/app"
ARG APP_USER="filebeat"
ARG APP_GROUP="${APP_USER}"
ARG APP_UID="1000"
ARG APP_GID="${APP_UID}"
ARG APP_URL="https://artifacts.elastic.co/downloads/beats/filebeat"
ARG APP_VERSION="7.16.3"

ARG DEBIAN_FRONTEND=noninteractive

ENV PATH="${APP_DIR}:${PATH}"

SHELL ["/bin/bash", "-c"]

RUN apt-get update && \
    apt-get install wget -y

RUN groupadd -g "${APP_GID}" "${APP_GROUP}" && \
    useradd -u "${APP_UID}" -g "${APP_GID}" -s /bin/bash -m -d "${APP_DIR}" "${APP_USER}"

WORKDIR /tmp

RUN wget "${APP_URL}/filebeat-${APP_VERSION}-linux-x86_64.tar.gz" -O filebeat.tar.gz && \
    tar -xf filebeat.tar.gz -C "${APP_DIR}" --strip-components=1 && \
    rm -f filebeat.tar.gz

WORKDIR "${APP_DIR}"

RUN chown --recursive "${APP_USER}":"${APP_USER}" "${APP_DIR}"

USER "${APP_USER}"

CMD ["/bin/bash", "entrypoint.sh"]