#include <Arduino.h>
#include <BLEDevice.h>
#include <BLEServer.h>
#include <BLEUtils.h>
#include <BLE2902.h>

// Replace these UUIDs with the ones used in your Android app
#define FIREWORK_SERVICE_UUID "0000XXXX-0000-1000-8000-00805f9b34fb"
#define FIREWORK_COMMAND_CHARACTERISTIC_UUID "0000YYYY-0000-1000-8000-00805f9b34fb"

#define FIREWORK1_PIN 25
#define FIREWORK2_PIN 26

BLECharacteristic *pFireworkCommandCharacteristic;

class FireworkCommandCallback : public BLECharacteristicCallbacks {
  void onWrite(BLECharacteristic *pCharacteristic) {
    uint8_t fireworkNumber = pCharacteristic->getValue()[0];

    if (fireworkNumber == 1) {
      digitalWrite(FIREWORK1_PIN, HIGH);
      delay(100); // Adjust this delay to meet the requirements of your specific relay
      digitalWrite(FIREWORK1_PIN, LOW);
    } else if (fireworkNumber == 2) {
      digitalWrite(FIREWORK2_PIN, HIGH);
      delay(100); // Adjust this delay to meet the requirements of your specific relay
      digitalWrite(FIREWORK2_PIN, LOW);
    }
  }
};

void setup() {
  Serial.begin(115200);
  pinMode(FIREWORK1_PIN, OUTPUT);
  pinMode(FIREWORK2_PIN, OUTPUT);

  digitalWrite(FIREWORK1_PIN, LOW);
  digitalWrite(FIREWORK2_PIN, LOW);

  BLEDevice::init("Firework Launcher");
  BLEServer *pServer = BLEDevice::createServer();
  BLEService *pFireworkService = pServer->createService(FIREWORK_SERVICE_UUID);

  pFireworkCommandCharacteristic = pFireworkService->createCharacteristic(
    FIREWORK_COMMAND_CHARACTERISTIC_UUID,
    BLECharacteristic::PROPERTY_WRITE
  );

  pFireworkCommandCharacteristic->setCallbacks(new FireworkCommandCallback());
  pFireworkCommandCharacteristic->addDescriptor(new BLE2902());

  pFireworkService->start();
  BLEAdvertising *pAdvertising = BLEDevice::getAdvertising();
  pAdvertising->addServiceUUID(FIREWORK_SERVICE_UUID);
  pAdvertising->setScanResponse(false);
  pAdvertising->setMinPreferred(0x06);
  BLEDevice::startAdvertising();
}

void loop() {
  // Main loop of the ESP32 program, implement any additional functionality or power management features
  // ...
}
