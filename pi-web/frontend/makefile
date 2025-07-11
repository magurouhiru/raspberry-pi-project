# package.json の "version": "1.2.3", からバージョン取得
VERSION := $(shell jq -r .version package.json)
IMAGE_NAME_LOCAL := pi-web_frontend
IMAGE_NAME_REMOTE := ghcr.io/magurouhiru/raspberry-pi-project/pi-web_frontend

build_local:
	docker build \
		-t $(IMAGE_NAME_LOCAL):$(VERSION) \
		-t $(IMAGE_NAME_LOCAL):latest \
		--load .

build:
	docker buildx build --platform linux/amd64,linux/arm64 \
		-t $(IMAGE_NAME_REMOTE):$(VERSION) \
		--push .

push:
	docker buildx build --platform linux/amd64,linux/arm64 \
		-t $(IMAGE_NAME_REMOTE):$(VERSION) \
		--push .
