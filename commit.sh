#!/bin/bash

read -p "Enter commit message: " message
read -p "Enter version tag (e.g. v1.2.3): " vTag
read -p "Enter tag message: " tagMessage


# Set source and target directories
SOURCE="./log"
TARGET="./LocalLogs"

# Create target directory if it doesn't exist
mkdir -p "$TARGET"

# Move all files from logs to LocalLogs
mv "$SOURCE"/* "$TARGET"/

# Optional: delete any remaining files in logs folder (should be none)
rm -f "$SOURCE"/*

echo "All files moved from $SOURCE to $TARGET."

if [ -z "$message" ]; then
    echo "Commit message cannot be empty."
    exit 1
fi

echo ".gradle/" >> .gitignore
git rm -r --cached .gradle

echo "Adding changes..."
git add .

echo "Committing..."
git commit -m "$message"

echo "Pulling latest changes..."
git pull origin main --rebase

echo "Pushing..."
git push origin main

echo "Creating tag..."
git tag -a "$vTag" -m "$tagMessage"

echo "Pushing tag..."
git push origin "$vTag"

echo "Commit complete."