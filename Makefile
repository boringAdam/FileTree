SHELL := /bin/bash

build:
	podman build --tag filetree_app -f Dockerfile .
run: build
	podman pod create -p 3306:3306 -p 8081:8081 -p 8082:8082 filetree_pod
	podman run -dt --rm --pod filetree_pod -e MYSQL_ROOT_PASSWORD=admin --name filetree_mysql mysql
	sleep 10
	podman run -dt --rm --pod filetree_pod -e USERNAME=user1 -e PORT=8081 --name user1 localhost/filetree_app:latest
	podman run -dt --rm --pod filetree_pod -e USERNAME=user2 -e PORT=8082 --name user2 localhost/filetree_app:latest
