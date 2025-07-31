package com.example.emoai;

import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;
import java.io.OutputStream;

public class wifi_connect extends AppCompatActivity {

    EditText wifi_name,wifi_password;
    Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_wifi_connect);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        wifi_name = findViewById(R.id.wifi_name_input);
        wifi_password = findViewById(R.id.wifi_password_input);
        btn = findViewById(R.id.wifi_connect_btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String SSID = wifi_name.getText().toString();
                String password = wifi_password.getText().toString();
                BluetoothSocket socket = MyBluetoothManager.getInstance().getSocket();

                if (socket != null && socket.isConnected()) {
                    try {
                        OutputStream outputStream = socket.getOutputStream();
                        outputStream.write(password.getBytes());
                        Toast.makeText(wifi_connect.this, "Data sent !", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(wifi_connect.this, "error", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(wifi_connect.this, "Not connected!", Toast.LENGTH_SHORT).show();
                }


            }
        });



    }
}