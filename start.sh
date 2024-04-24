#!/bin/sh

adduser -DH -G root $USERNAME
chmod -R 775 /app
su -c "java -jar app.jar" "$USERNAME"
