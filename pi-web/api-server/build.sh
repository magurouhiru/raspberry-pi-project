#!/bin/bash

echo "Install packages..."
sbt update

echo "Remove old dist..."
rm -rf target/universal

echo "Build play..."
sbt Universal/packageZipTarball
