#!/bin/bash

read -p "Enter commit message: " message
read -p "Enter version tag (e.g. v1.2.3): " vTag
read -p "Enter tag message: " tagMessage

if [ -z "$message" ]; then
    echo "Commit message cannot be empty."
    exit 1
fi

echo "Cleaning old class files....."
find . -name "*.class" -delete

echo "Compiling....."
javac -d out $(find . -name "*.java")

if [ $? -ne 0 ]; then
    echo "Compilation failed. Aborting."
    exit 1
fi

echo "Ensuring accurate manifest....."
echo "Main-Class: game.NigerianEconomyGame" > manifest.txt
echo "" >> manifest.txt

echo "Packaging jar....."
jar --create --file build/NigerianEconomy-$vTag.jar --manifest manifest.txt -C out .

if [ $? -ne 0 ]; then
    echo "Jar creation failed. Aborting."
    exit 1
fi

echo "Adding changes....."
git add .

echo "Committing....."
git commit -m "$message"

echo "Pulling latest changes....."
git pull origin main --rebase

echo "Pushing to main....."
git push origin main

echo "Creating tag....."
git tag -a "$vTag" -m "$tagMessage"

echo "Pushing tag....."
git push origin "$vTag"

echo "Release build complete."
echo "Jar created: NigerianEconomy-$vTag.jar"