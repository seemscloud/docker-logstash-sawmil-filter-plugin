FROM debian:bullseye-slim

ARG DEBIAN_FRONTEND=noninteractive

SHELL ["/bin/bash", "-c"]

RUN apt-get update && \
    apt-get install curl -y

COPY ./entrypoint.sh .

ENTRYPOINT [ "/bin/bash", "entrypoint.sh" ]