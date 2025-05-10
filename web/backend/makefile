backend-build: backend-init
	@echo "Building backend..."
	cross build --target armv7-unknown-linux-musleabihf --release
backend-init:
	@echo "init backend-init..."
	rustup target add armv7-unknown-linux-musleabihf
