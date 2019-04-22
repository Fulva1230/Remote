package com.noxdawn.remote;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import static android.widget.Toast.LENGTH_SHORT;
import static com.noxdawn.remote.btconnect.BtItemAdapter.*;

public class Controller extends AppCompatActivity {
    
    private BluetoothSocket socket;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller);
        if (getIntent() != null && getIntent().hasExtra(BLUETOOTH_ADDRESS) && getIntent().hasExtra(BLUETOOTH_NAME)) {
            TextView bluetoothInform = findViewById(R.id.controller_text);
            bluetoothInform.setText(String.format("connecting to : %s", getIntent().getStringExtra(BLUETOOTH_NAME)));
            BluetoothDevice device = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(getIntent().getStringExtra(BLUETOOTH_ADDRESS));
            try {
                socket = device.createInsecureRfcommSocketToServiceRecord(SPP_UUID);
                socket.connect();
            } catch (IOException e) {
                Toast toastMessage = Toast.makeText(this, "connect attempt failed", LENGTH_SHORT);
                toastMessage.show();
                e.printStackTrace();
                finish();
            }
            bluetoothInform.setText(String.format("connected to :　%s", getIntent().getStringExtra(BLUETOOTH_NAME)));
        } else {
            finish();
        }
    }
}