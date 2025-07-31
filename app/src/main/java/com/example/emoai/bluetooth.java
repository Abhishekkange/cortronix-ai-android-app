package com.example.emoai;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class bluetooth  extends AppCompatActivity {

    BluetoothAdapter bluetoothAdapter;
    Button bluetoothBtn;
    public static final int BT_RESULT = 200;
    public static final int PERMISSION_CODE = 1001;
    String[] bluetoothPermissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bluetooth);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bluetoothBtn = findViewById(R.id.turnOnBluetoothBtn);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // Initialize permissions based on Android version
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            bluetoothPermissions = new String[]{
                    Manifest.permission.BLUETOOTH_SCAN,
                    Manifest.permission.BLUETOOTH_CONNECT,
                    Manifest.permission.BLUETOOTH_ADVERTISE
            };
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            bluetoothPermissions = new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            };
        } else {
            bluetoothPermissions = new String[]{}; // No permissions required pre-Marshmallow
        }

        bluetoothBtn.setOnClickListener(v -> {
            if (bluetoothAdapter == null) {
                Toast.makeText(this, "Your device doesn't support Bluetooth!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!bluetoothAdapter.isEnabled()) {
                if (!checkAllPermissions()) {
                    ActivityCompat.requestPermissions(this, bluetoothPermissions, PERMISSION_CODE);
                } else {
                    enableBluetooth();
                }
            } else {
                // Already enabled
                moveToNextScreen();
            }
        });
    }

    private boolean checkAllPermissions() {
        for (String permission : bluetoothPermissions) {
            if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private void enableBluetooth() {
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableBtIntent, BT_RESULT);
    }

    private void moveToNextScreen() {
        Toast.makeText(this, "Bluetooth already enabled", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, connect_to_codrix.class));
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BT_RESULT) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Bluetooth Enabled", Toast.LENGTH_SHORT).show();
                moveToNextScreen();
            } else {
                Toast.makeText(this, "Bluetooth Enable Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CODE) {
            boolean allGranted = true;
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    allGranted = false;
                    break;
                }
            }

            if (allGranted) {
                enableBluetooth();
            } else {
                Toast.makeText(this, "Bluetooth permissions are required", Toast.LENGTH_SHORT).show();
            }
        }
    }
}