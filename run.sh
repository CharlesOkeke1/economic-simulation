#!/bin/bash

echo "Compiling project..."

# Clean old class files
find . -name "*.class" -type f -delete

# Compile all Java files
javac $(find . -name "*.java")

if [ $? -ne 0 ]; then
    echo "Compilation failed."
    exit 1
fi

echo "Compilation successful."
echo "Running simulation..."

java game.AppMain