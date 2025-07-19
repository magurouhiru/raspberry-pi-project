# build.sbt の version := "1.2.3" からバージョン取得
VERSION := $(shell awk -F'"' '/version *:=/ {print $$2}' build.sbt)
IMAGE_NAME_LOCAL := pi-web_api-server
IMAGE_NAME := ghcr.io/magurouhiru/raspberry-pi-project/pi-web_api-server
DB_URL ?= jdbc:postgresql://db:5432/playdb
DB_USER ?= playuser
DB_PASSWORD ?= playpass
DB_NAME ?= playdb

build: build_local_k8s

build_local:
	docker build \
		--platform linux/arm64 \
		-t $(IMAGE_NAME_LOCAL):$(VERSION) \
		-t $(IMAGE_NAME_LOCAL):latest \
		--build-arg DB_URL=$(DB_URL) \
		--build-arg DB_USER=$(DB_USER) \
		--build-arg DB_PASSWORD=$(DB_PASSWORD) \
		--build-arg DB_NAME=$(DB_NAME) \
		--load .

build_local_k8s: build_local
	docker build \
		--platform linux/arm64 \
		-t $(IMAGE_NAME):$(VERSION) \
		--build-arg DB_URL=$(DB_URL) \
		--build-arg DB_USER=$(DB_USER) \
		--build-arg DB_PASSWORD=$(DB_PASSWORD) \
		--build-arg DB_NAME=$(DB_NAME) \
		--load .

push_wip:
	docker buildx build \
		--platform linux/arm64 \
		--builder $(BUILDER) \
		-t $(IMAGE_NAME):$(VERSION)-wip \
		--build-arg DB_URL=$(DB_URL) \
		--build-arg DB_USER=$(DB_USER) \
		--build-arg DB_PASSWORD=$(DB_PASSWORD) \
		--build-arg DB_NAME=$(DB_NAME) \
		--push .

push:
	docker buildx build \
		--platform linux/arm64 \
		--builder $(BUILDER) \
		-t $(IMAGE_NAME):$(VERSION) \
		--build-arg DB_URL=$(DB_URL) \
		--build-arg DB_USER=$(DB_USER) \
		--build-arg DB_PASSWORD=$(DB_PASSWORD) \
		--build-arg DB_NAME=$(DB_NAME) \
		--push .
