#!/bin/bash

cd /home/elpi/app

echo "removing old application files..."
rm -rf api-server
rm -rf logs

echo "unpacking Play application..."
mkdir api-server
tar -xzf api-server-*.tgz -C api-server

echo "Setting environment variables..."
export APP_ENV=prod
export DB_URL=jdbc:sqlite://home/elpi/app/playdb
export DEVICE_MOCK=false

echo "Creating database file..."
rm -f ./playdb
echo > ./playdb

echo "Starting Play application..."
exec ./api-server/api-server-*/bin/api-server
