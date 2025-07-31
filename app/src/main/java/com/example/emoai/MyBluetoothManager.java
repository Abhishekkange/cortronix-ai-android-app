package com.example.emoai;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

public class MyBluetoothManager {
    private static MyBluetoothManager instance;
    private BluetoothDevice device;
    private BluetoothSocket socket;

    private MyBluetoothManager() {}

    public static MyBluetoothManager getInstance() {
        if (instance == null) {
            instance = new MyBluetoothManager();
        }
        return instance;
    }

    public void setDevice(BluetoothDevice device) {
        this.device = device;
    }

    public BluetoothDevice getDevice() {
        return device;
    }

    public void setSocket(BluetoothSocket socket) {
        this.socket = socket;
    }

    public BluetoothSocket getSocket() {
        return socket;
    }
}