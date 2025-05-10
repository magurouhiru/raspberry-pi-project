.PHONY: all frontend server-build server-init

server-build: server-init
	@echo "Building server..."
	cross build --target armv7-unknown-linux-musleabihf --release
server-init: frontend
	@echo "init server..."
	rustup target add armv7-unknown-linux-musleabihf
frontend:
	$(MAKE) -C frontend front-build