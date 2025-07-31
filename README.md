# Cortronix Connect App

The **Cortronix Connect App** is a simple Android + ESP32-based Bluetooth communication application designed to connect with the **Cortronix Smart Robot**. Once connected, the app enables personalized interactions such as triggering animations, changing states, and sending custom commands via JSON.

## 🚀 Features

- 🔍 Scan for available Bluetooth devices
- 🔗 Pair and connect to the Cortronix robot
- 📤 Send custom commands (e.g., `eye_open`, `sleep_mode`, etc.)
- 🧠 Personalized interactions using JSON-based communication

## 📱 App Structure

- **BluetoothService.java** – Handles all Bluetooth communication
- **MainActivity.java** – UI and logic for device scanning and connection
- **DeviceListAdapter.java** – Displays found Bluetooth devices

## 🔧 ESP32 (Robot Side)

- Receives JSON commands via Bluetooth Serial
- Parses and executes actions such as animations, lighting, or sounds



<img width="268" height="411" alt="Screenshot 2025-08-01 at 2 35 28 AM" src="https://github.com/user-attachments/assets/c28f9ebb-1f8d-4d26-9a8d-4604d2674898" />
<img width="241" height="412" alt="Screenshot 2025-08-01 at 2 35 54 AM" src="https://github.com/user-attachments/assets/8161f2c8-84b5-4755-b58b-1738739220aa" />
<img width="235" height="396" alt="Screenshot 2025-08-01 at 2 36 07 AM" src="https://github.com/user-attachments/assets/25b34cd4-0dc9-485a-bd71-bf4b55b485c0" />
