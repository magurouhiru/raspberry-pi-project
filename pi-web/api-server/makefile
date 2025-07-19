# build.sbt の version := "1.2.3" からバージョン取得
VERSION := $(shell awk -F'"' '/version *:=/ {print $$2}' build.sbt)
IMAGE_NAME_LOCAL := pi-web_api-server
IMAGE_NAME := ghcr.io/magurouhiru/raspberry-pi-project/pi-web_api-server

build: build_local_k8s

build_local:
	docker build \
		--platform linux/arm64 \
		-t $(IMAGE_NAME_LOCAL):$(VERSION) \
		-t $(IMAGE_NAME_LOCAL):latest \
		--load .

build_local_k8s: build_local
	docker build \
		--platform linux/arm64 \
		-t $(IMAGE_NAME):$(VERSION) \
		--load .

push_wip:
	docker buildx build \
		--platform linux/arm64 \
		--builder $(BUILDER) \
		-t $(IMAGE_NAME):$(VERSION)-wip \
		--push .

push:
	docker buildx build \
		--platform linux/arm64 \
		--builder $(BUILDER) \
		-t $(IMAGE_NAME):$(VERSION) \
		--push .
