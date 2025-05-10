ARG NODEJS_VERSION

# 公式イメージを使用
FROM node:${NODEJS_VERSION}-bookworm

ARG RUST_VERSION
ARG ANGULAR_VERSION

RUN apt-get update \
    && apt-get upgrade -y \
    && apt-get install -y ca-certificates git curl unzip gcc \
    && apt-get install -y sqlite3 libsqlite3-dev
RUN  curl --proto '=https' --tlsv1.2 -sSf https://sh.rustup.rs | sh -s -- -y --target ${RUST_VERSION} --no-update-default-toolchain \
    && . "$HOME/.cargo/env" \
    && cargo install cross \
    && cargo install cargo-watch \
    && npm install -g @angular/cli@${ANGULAR_VERSION}
