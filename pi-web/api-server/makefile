# build.sbt の version := "1.2.3" からバージョン取得
VERSION := $(shell awk -F'"' '/version *:=/ {print $$2}' build.sbt)
IMAGE_NAME := ghcr.io/magurouhiru/pi-web_api-server

build:
	docker build \
		-t $(IMAGE_NAME):$(VERSION) \
		-t $(IMAGE_NAME):latest \
		--load .

push:
	docker buildx build --platform linux/amd64,linux/arm64 \
		-t $(IMAGE_NAME):$(VERSION) \
		--push .

release: push
