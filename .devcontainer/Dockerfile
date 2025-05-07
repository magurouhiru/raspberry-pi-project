ARG DEBIAN_VERSION
ARG NODEJS_VERSION

# 公式イメージを使用
FROM node:${NODEJS_VERSION}-bookworm

ARG RUST_VERSION
ARG ANGULAR_VERSION
ARG WEB_USERNAME
ARG WEB_USER_UID
ARG WEB_USER_GID
ARG WEB_WORKDIR_NAME
ARG WEB_WORKDIR=/home/${WEB_USERNAME}/${WEB_WORKDIR_NAME}

RUN apt-get update \
    && apt-get upgrade -y \
    && apt-get install -y ca-certificates git curl unzip gcc
# && install -m 0755 -d /etc/apt/keyrings \
# && curl -fsSL https://download.docker.com/linux/debian/gpg -o /etc/apt/keyrings/docker.asc \
# && chmod a+r /etc/apt/keyrings/docker.asc \
# && echo \
# "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.asc] https://download.docker.com/linux/debian \
# $(. /etc/os-release && echo "$VERSION_CODENAME") stable" | \
# tee /etc/apt/sources.list.d/docker.list > /dev/null \
# && apt-get update \
# && (. /etc/os-release && echo "$VERSION_CODENAME") \
# && apt-get install -y docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin \
#     && groupadd --gid ${WEB_USER_GID} ${WEB_USERNAME} \
#     && useradd --uid ${WEB_USER_UID} --gid ${WEB_USER_GID} -m ${WEB_USERNAME} \
#     && mkdir ${WEB_WORKDIR} \
#     && chown -R ${WEB_USERNAME}:${WEB_USERNAME} /home/${WEB_USERNAME}

# USER ${WEB_USERNAME}
# WORKDIR ${WEB_WORKDIR}
RUN  curl --proto '=https' --tlsv1.2 -sSf https://sh.rustup.rs | sh -s -- -y --target ${RUST_VERSION} --no-update-default-toolchain \
    && . "$HOME/.cargo/env" \
    && cargo install cross \
    # && curl -o- https://fnm.vercel.app/install | bash
    # && export PATH="/root/.local/share/fnm:$PATH" \
    # && eval "$(fnm env)" \
    # && fnm install ${NODEJS_VERSION}
    && npm install -g @angular/cli@${ANGULAR_VERSION}
