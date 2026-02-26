#!/bin/bash

# Ask for commit message
read -p "Enter commit message: " message
read -p "Enter Version tag: " vTag
read -p "Enter tag message: " tagMessage

# Stop if message is empty
if [ -z "$message" ]; then
    echo "Commit message cannot be empty."
    exit 1
fi

echo "Adding changes....."
git add .

echo "Committing....."
git commit -m "$message"

echo "Downloading remote changes....."
git pull origin main --rebase

echo "Pushing to origin/main....."
git push origin main

echo "tagging....."
git tag -a "$vTag" -m "$tagMessage"
git push origin --tags

echo "Upload complete."
