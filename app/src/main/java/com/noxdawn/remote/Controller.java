package com.noxdawn.remote;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.noxdawn.remote.btconnect.OStreamFetcher;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.atomic.AtomicReference;

import static android.widget.Toast.LENGTH_SHORT;
import static com.noxdawn.remote.btconnect.BtItemAdapter.BLUETOOTH_ADDRESS;
import static com.noxdawn.remote.btconnect.BtItemAdapter.BLUETOOTH_NAME;

public class Controller extends AppCompatActivity {
    
    private JoystickWrapper leftJoy;
    private JoystickWrapper rightJoy;
    private SeekbarWrapper servo_first;
    private OStreamFetcher bleStrFet;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller);
        if (getIntent() != null && getIntent().hasExtra(BLUETOOTH_ADDRESS) && getIntent().hasExtra(BLUETOOTH_NAME)) {
            TextView bluetoothInform = findViewById(R.id.controller_text);
            bluetoothInform.setText(String.format("connecting to : %s", getIntent().getStringExtra(BLUETOOTH_NAME)));
            BluetoothDevice device = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(getIntent().getStringExtra(BLUETOOTH_ADDRESS));
            final AtomicReference<OutputStream> oStreamR = new AtomicReference<>();
            bleStrFet = new OStreamFetcher(device, oStreamR, this, bluetoothInform);
            Thread bConne = new Thread(bleStrFet);
            leftJoy = new JoystickWrapper(findViewById(R.id.leftJoy), oStreamR, findViewById(R.id.leftJoyInform), "left");
            rightJoy = new JoystickWrapper(findViewById(R.id.rightJoy), oStreamR, findViewById(R.id.rightJoyInform), "right");
            servo_first = new SeekbarWrapper(findViewById(R.id.servo1), "servo1", oStreamR);
            bConne.start();
        } else {
            finish();
        }
    
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            bleStrFet.close();
        } catch (IOException e) {
            Toast toastMessage = Toast.makeText(this, "failed to disconnect from the device", LENGTH_SHORT);
            toastMessage.show();
            e.printStackTrace();
        }
    }
}