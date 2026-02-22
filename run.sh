#!/bin/bash
find . -name "*.class" -delete
javac $(find game -name "*.java")
java game.NigerianEconomyGame