#!/bin/bash

# Create directories
mkdir -p AndroidApp/app/src/main/java/com/example/fireworklauncher
mkdir -p AndroidApp/app/src/main/res/layout
mkdir -p ESP32_FireworkLauncher

# Android app files
touch AndroidApp/app/build.gradle
touch AndroidApp/app/src/main/AndroidManifest.xml
touch AndroidApp/app/src/main/java/com/example/fireworklauncher/MainActivity.java
touch AndroidApp/app/src/main/res/layout/activity_main.xml

# ESP32 Arduino files
touch ESP32_FireworkLauncher/ESP32_FireworkLauncher.ino

