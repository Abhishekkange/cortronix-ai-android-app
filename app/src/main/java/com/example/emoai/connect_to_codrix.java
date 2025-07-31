package com.example.emoai;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;
import java.util.UUID;

public class connect_to_codrix extends AppCompatActivity {

    Button connectCodronixBtn;
    BluetoothAdapter adapter;
    UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_connect_to_codrix);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        adapter = BluetoothAdapter.getDefaultAdapter();

        connectCodronixBtn = findViewById(R.id.connectToCodronixBtn);

        connectCodronixBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //check for bluetooth permission
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    if (checkSelfPermission(android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(connect_to_codrix.this, "Request Permission...", Toast.LENGTH_SHORT).show();

                        requestPermissions(new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 1001);
                    }
                    else {
                        Toast.makeText(connect_to_codrix.this, "Start Discovery...", Toast.LENGTH_SHORT).show();
                        adapter.startDiscovery();
                    }
                }

            }
        });

        IntentFilter intentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(receiver,intentFilter);




    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @SuppressLint("MissingPermission")
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                if (device != null && device.getName() != null && device.getName().equals("Codronix")) {
                    Toast.makeText(context, "Codronix Found", Toast.LENGTH_SHORT).show();

                    MyBluetoothManager.getInstance().setDevice(device);

                    try {
                        BluetoothSocket socket = device.createRfcommSocketToServiceRecord(MY_UUID);

                        // Cancel discovery before connecting
                        BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                        socket.connect();  // blocking call
                        Toast.makeText(context, "Connected to Codronix!", Toast.LENGTH_SHORT).show();

                        MyBluetoothManager.getInstance().setSocket(socket);

                        // Start MainActivity
                        Intent intentStart = new Intent(context, wifi_connect.class);
                        intentStart.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // IMPORTANT when starting activity from receiver
                        context.startActivity(intentStart);

                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(context, "Connection failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    };
    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1001) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, Enable blueetoth
                adapter.startDiscovery();

            } else {
                Toast.makeText(this, "Bluetooth permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}