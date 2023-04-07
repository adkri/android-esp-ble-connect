# TODO.md

This document provides detailed instructions for completing the Firework Launcher project, including the Android app and the ESP32 microcontroller system. The tasks are broken down into subtasks to provide a clear roadmap for the team of engineers.

## Android App

### 1. Implement BLE connection logic
   - Create a BluetoothManager class to handle BLE communication.
      - Add the no.nordicsemi.android:ble library to the build.gradle file.
      - Define methods for the following actions:
        - Scanning for available BLE devices
        - Connecting to a specific device
        - Disconnecting from a device
        - Sending commands to the device
      - Implement callback interfaces for connection state changes and command responses.
      - Register the necessary permissions and features in the AndroidManifest.xml file.
   - Update MainActivity.java to utilize the BluetoothManager class.
      - Initialize a BluetoothManager instance in the onCreate method.
      - Implement a callback method to handle BLE connection status changes (e.g., connected, disconnected) and update the UI accordingly.
      - Implement the logic to handle button clicks for connecting and disconnecting from the ESP32 device.
        - Call the appropriate BluetoothManager methods for scanning, connecting, and disconnecting.
        - Provide user feedback on the connection status (e.g., toast messages, dialogs).

### 2. Implement firework launch buttons logic
   - In MainActivity.java, implement the logic for the "Launch 1" and "Launch 2" buttons.
      - Create a method for each button's click listener that sends the appropriate command to the ESP32 device using the BluetoothManager class.
        - The command can be a simple string or integer value representing the firework to launch.
      - Update the UI to show the status of the firework launch (e.g., a progress bar or spinner, success/failure icons).
      - Handle responses from the ESP32 device, such as command acknowledgements or error messages.

### 3. Add error handling and user feedback
   - In the BluetoothManager class, handle possible errors during BLE communication (e.g., device not found, connection timeout, write failures).
      - Define appropriate error codes or messages for each type of error.
      - Implement callback methods to report errors to the calling activity.
   - Update MainActivity.java to handle errors reported by the BluetoothManager class.
      - Display appropriate messages or dialogs to inform the user about the status of the BLE connection and firework launches.
      - Handle edge cases, such as the user turning off Bluetooth or moving out of range.

### 4. UI refinements and testing
   - Refine the UI design, if necessary, to ensure it is user-friendly and visually appealing.
      - Optimize the layout for different screen sizes and orientations.
      - Use appropriate colors, typography, and iconography.
   - Test the Android app on various devices to ensure compatibility and proper functionality.
      - Perform regression testing after implementing new features or fixing bugs.
      - Test corner cases, such as low battery or poor Bluetooth signal conditions.

## ESP32 Microcontroller

### 1. Implement BLE service and characteristic handling
   - Refine the existing LaunchCallbacks class to handle more complex commands, if necessary.
      - Define a command structure or protocol for exchanging information between the Android app and the ESP32 device.
   - Add error handling to the onWrite method of the LaunchCallbacks class to handle invalid commands gracefully.
      - Define error codes or messages to report back to the Android app.
      - Implement logic to send error messages to the app when necessary.
### 2. Implement relay control logic
   - Refine the relay control logic in the onWrite method of the LaunchCallbacks class, if necessary.
   - Define constant values or macros for the GPIO pins connected to the relays.
   - Configure the GPIO pins as outputs in the setup function of the ESP32_FireworkLauncher.ino file.
   - Ensure the relays are activated safely and reliably.
      - Implement a debounce mechanism to prevent false triggers.
      - Add any necessary delays or additional logic to guarantee the proper operation of the fireworks launch system.
   - Implement logic to send an acknowledgement message to the Android app after successfully activating a relay.

### 3. Power management and sleep mode
   - Implement power management features to conserve battery life when the ESP32 device is not actively launching fireworks.
      - Research sleep modes or other power-saving techniques provided by the ESP32 hardware.
      - Implement a function to put the ESP32 into a low-power state when idle.
      - Implement a function to wake up the ESP32 when a BLE connection is initiated or a command is received.
      - Test the power management features to ensure the ESP32 device operates efficiently and wakes up as expected.

### 4. Safety features
   - Implement safety features to prevent accidental firework launches.
      - Add an authentication mechanism (e.g., a simple PIN code) to the BLE communication to ensure only authorized users can connect and control the device.
          - Define a command for sending the PIN code from the Android app to the ESP32 device.
          - Implement logic in the onWrite method of the LaunchCallbacks class to verify the PIN code before allowing access to the firework launch commands.
      - Implement a failsafe mechanism in case of a malfunction or unexpected event (e.g., a hardware watchdog timer).
          - Research the ESP32's built-in watchdog timer capabilities.
          - Implement a function to reset the watchdog timer after each successful command execution.
          - Define a timeout value for the watchdog timer and implement a function to handle the timeout event (e.g., safely shutting down the relays and resetting the system).

### 5. Testing and debugging
   - Test the ESP32 microcontroller code thoroughly to ensure proper functionality and safety.
      - Test the relay control logic with various commands and conditions (e.g., rapid switching between fireworks, invalid commands).
      - Test the authentication mechanism to ensure unauthorized users cannot control the device.
      - Test the failsafe mechanism (e.g., by simulating a malfunction or deadlock in the code).
   - Debug any issues that arise during testing and refine the code as necessary.

## Integration and Final Testing
   - After completing the tasks for both the Android app and ESP32 microcontroller, integrate the two components and perform end-to-end testing of the entire system.
      - Test the BLE connection process, including scanning, connecting, and disconnecting.
      - Test the firework launch commands and verify that the relays are activated as expected.
      - Test the error handling and user feedback mechanisms to ensure a smooth user experience.
      - Test the power management features of the ESP32 device to ensure efficient operation.
      - Test the safety features, including the authentication mechanism and failsafe mechanism.
   - Ensure the system functions reliably, safely, and as expected.
      - Address any issues or bugs discovered during testing and refine the code as necessary.
      - Perform regression testing after fixing bugs or implementing new features.

After completing the tasks outlined in this document, the Firework Launcher project should be a fully functional, safe, and reliable system for remotely launching fireworks using an Android app and an ESP32 microcontroller.