# Firework Launcher

This project is an Android app and ESP32 microcontroller system designed to launch fireworks using a Bluetooth Low Energy (BLE) connection.

## Architecture

The system consists of two main components:

1. **Android App**: The app provides a user interface with three buttons: one to connect to the ESP32 device via BLE, and two to launch fireworks. It handles user input and communicates with the ESP32 microcontroller using the `BluetoothManager` class. The `BluetoothManager` class manages the BLE connection, services, and characteristics for the Android app.

2. **ESP32 Microcontroller**: The ESP32 runs an Arduino sketch that sets it up as a BLE server. It listens for incoming firework launch commands sent from the Android app and triggers the appropriate GPIO pins to launch the corresponding fireworks using connected relays.

## How It Works

1. The user opens the Android app and presses the "Connect" button.
2. The app scans for nearby ESP32 devices and connects to the target device via BLE.
3. The user presses one of the firework launch buttons in the app.
4. The app sends a firework launch command to the ESP32 device over the BLE connection.
5. The ESP32 device receives the command and triggers the appropriate GPIO pin to launch the corresponding firework using a connected relay.
6. The firework is launched, and the GPIO pin is reset to its default state.

## Getting Started

### Prerequisites

- Android Studio
- Arduino IDE with ESP32 support

### Running the Android App

1. Clone this repository.
2. Open the `FireworkLauncherApp` directory in Android Studio.
3. Connect an Android device or start an emulator.
4. Update the service and characteristic UUIDs in `BluetoothManager.java` to match your ESP32 device's UUIDs.
5. Build and run the app on your Android device or emulator.

### Running the ESP32 Microcontroller Code

1. Open the `ESP32_FireworkLauncher` directory in the Arduino IDE.
2. Connect your ESP32 device to your computer via USB.
3. Update the service and characteristic UUIDs in `ESP32_FireworkLauncher.ino` to match the UUIDs used in your Android app.
4. Select the appropriate board and port for your ESP32 device in the Arduino IDE.
5. Upload the `ESP32_FireworkLauncher.ino` sketch to your ESP32 device.


## Usage
1. Power the ESP32 board and make sure the relays are connected correctly.
2. Open the Android app on your device.
3. Click the "Connect" button to establish a BLE connection with the ESP32 device.
4. Click "Launch 1" or "Launch 2" to send a command to the ESP32, which will trigger the corresponding relay and launch the firework.



## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.
