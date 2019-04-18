package com.noxdawn.remote;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.view.View;
import android.widget.ListView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.noxdawn.remote.btconnect.BtItemAdapter;

public class MainActivity extends AppCompatActivity {
    
    static final private int REQUEST_ENABLE_BT = 1;
    private BluetoothAdapter bluetoothAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            finish();
            // Device doesn't support Bluetooth
        } else {
            if (!bluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            } else {
                initBluetooth();
            }
        }
    }
    
    private void initBluetooth() {
        ListView btItems = findViewById(R.id.btItems);
        btItems.setAdapter(new BtItemAdapter(bluetoothAdapter));
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ENABLE_BT && resultCode == RESULT_OK) {
            initBluetooth();
        }
    }
}
