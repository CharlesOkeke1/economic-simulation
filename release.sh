#!/bin/bash

read -p "Enter commit message: " message
read -p "Enter version tag (e.g. v1.2.3): " vTag
read -p "Enter tag message: " tagMessage

if [ -z "$message" ]; then
    echo "Commit message cannot be empty."
    exit 1
fi

echo "Running Gradle clean..."
./gradlew clean

if [ $? -ne 0 ]; then
    echo "Gradle clean failed. Aborting."
    exit 1
fi

echo "Building project with Gradle..."
./gradlew build

if [ $? -ne 0 ]; then
    echo "Gradle build failed. Aborting."
    exit 1
fi

echo "Gradle build successful."

echo "Adding changes..."
git add .

echo "Committing..."
git commit -m "$message"

echo "Pulling latest changes..."
git pull origin main --rebase

echo "Pushing to main..."
git push origin main

echo "Creating tag..."
git tag -a "$vTag" -m "$tagMessage"

echo "Pushing tag..."
git push origin "$vTag"

echo "Release build complete."
echo "Jar should be located in: build/libs/"