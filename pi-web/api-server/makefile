# build.sbt の version := "1.2.3" からバージョン取得
VERSION := $(shell awk -F'"' '/version *:=/ {print $$2}' build.sbt)
IMAGE_NAME_LOCAL := pi-web_api-server
IMAGE_NAME_REMOTE := ghcr.io/magurouhiru/raspberry-pi-project/pi-web_api-server

build_local:
	docker build \
		-t $(IMAGE_NAME):$(VERSION) \
		-t $(IMAGE_NAME_LOCAL):latest \
		--load .

build_remote:
	docker buildx build --platform linux/amd64,linux/arm64 \
		-t $(IMAGE_NAME_REMOTE):$(VERSION) \
		--push .

push:
	docker buildx build --platform linux/amd64,linux/arm64 \
		-t $(IMAGE_NAME_REMOTE):$(VERSION) \
		--push .
