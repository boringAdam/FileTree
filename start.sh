#!/bin/sh

adduser -DH -G root $USERNAME
su -c "java -jar app.jar" "$USERNAME"
