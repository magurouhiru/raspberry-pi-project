#!/bin/bash

echo "Install packages..."
npm ci

echo "Build angular..."
npm run build
