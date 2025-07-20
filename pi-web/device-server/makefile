# app/__init__.py の __version__ = "1.2.3" からバージョン取得
VERSION = $(shell awk -F'"' '/__version__/ {print $$2}' app/__init__.py)
IMAGE_NAME_LOCAL := pi-web_device-server
IMAGE_NAME := ghcr.io/magurouhiru/raspberry-pi-project/pi-web_device-server

build: build_local_k8s

build_local:
	docker build \
		--platform linux/arm64 \
		-t $(IMAGE_NAME_LOCAL):$(VERSION) \
		-t $(IMAGE_NAME_LOCAL):latest \
		--build-arg MOCK_MODE=true \
		--load .

build_local_k8s: build_local
	docker build \
		--platform linux/arm64 \
		-t $(IMAGE_NAME):$(VERSION) \
		--build-arg MOCK_MODE=true \
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
