# package.json の "version": "1.2.3", からバージョン取得
VERSION := $(shell jq -r .version package.json)
IMAGE_NAME_LOCAL := pi-web_frontend
IMAGE_NAME := ghcr.io/magurouhiru/raspberry-pi-project/pi-web_frontend

build: build_local_k8s

build_local:
	docker build \
		--platform linux/arm64 \
		-t $(IMAGE_NAME_LOCAL):$(VERSION) \
		-t $(IMAGE_NAME_LOCAL):latest \
		--build-arg API_SERVER_URL=api-server:9000 \
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
