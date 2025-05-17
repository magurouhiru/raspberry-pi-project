backend-build: backend-init
	@echo "Building backend..."
	@{ \
		cross build --target armv7-unknown-linux-musleabihf --release; \
	} || { \
		$(MAKE) backend-build-error; \
	}

backend-build-error:
	@echo "ERROR backend-build..."
	@echo "ERROR uninstall toolchain..."
	rustup uninstall stable
	@echo "RETRY backend-build..."
	cross build --target armv7-unknown-linux-musleabihf --release

backend-init:
	@echo "init backend-init..."
	rustup target add armv7-unknown-linux-musleabihf
