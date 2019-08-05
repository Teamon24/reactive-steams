#!/usr/bin/env bash

docker-compose down
docker-compose up -d
sleep 1
docker exec reactive-streams bash -c "mongo test_db /setup/mongodb-setup.js"