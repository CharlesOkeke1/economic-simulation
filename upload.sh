#!/bin/bash

# Ask for commit message
read -p "Enter commit message: " message

# Stop if message is empty
if [ -z "$message" ]; then
    echo "Commit message cannot be empty."
    exit 1
fi

echo "Adding changes..."
git add .

echo "Committing..."
git commit -m "$message"

echo "Pushing to origin/main..."
git push origin main

echo "Upload complete."
