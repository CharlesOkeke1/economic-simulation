#!/bin/bash

read -p "Enter commit message: " message

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

echo "Commit complete."