ARG NODEJS_VERSION

# 公式イメージを使用
FROM node:${NODEJS_VERSION}-slim

ARG RUST_VERSION
ARG ANGULAR_VERSION
ARG WEB_USERNAME
ARG WEB_USER_UID
ARG WEB_USER_GID
ARG WEB_WORKDIR_NAME
ARG WEB_WORKDIR=/home/${WEB_USERNAME}/${WEB_WORKDIR_NAME}

RUN apt-get update \
    && apt-get upgrade -y \
    && apt-get install -y git curl gcc \
    && npm install -g @angular/cli@${ANGULAR_VERSION} \
    && groupadd --gid ${WEB_USER_GID} ${WEB_USERNAME} \
    && useradd --uid ${WEB_USER_UID} --gid ${WEB_USER_GID} -m ${WEB_USERNAME} \
    && mkdir ${WEB_WORKDIR} \
    && chown -R ${WEB_USERNAME}:${WEB_USERNAME} /home/${WEB_USERNAME}

USER ${WEB_USERNAME}
WORKDIR ${WEB_WORKDIR}
RUN  curl --proto '=https' --tlsv1.2 -sSf https://sh.rustup.rs | sh -s -- -y --target ${RUST_VERSION} --no-update-default-toolchain
