#!/bin/sh
sudo git pull
sudo mvn clean install -DskipTests=true -e
sudo docker-compose build
sudo docker-compose up

