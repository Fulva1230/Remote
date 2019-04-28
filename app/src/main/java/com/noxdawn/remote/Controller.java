package com.noxdawn.remote;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.widget.Switch;
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
    private SeekbarWrapper servo_second;
    private SeekbarWrapper servo_third;
    private OStreamFetcher bleStrFet;
    private static final String LEFT_CHANGE_DIR_KEY = "LEFT_CHANGE";
    private static final String RIGHT_CHANGE_DIR_KEY = "RIGHT_CHANGE";
    
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
            //initialization of controll views
            controllViewInit(oStreamR);
            bConne.start();
        } else {
            finish();
        }
    
    }
    
    private void controllViewInit(AtomicReference<OutputStream> oStreamR) {
        servo_first = new SeekbarWrapper(findViewById(R.id.servo1), Commands.SERVO_FIRST, oStreamR);
        servo_second = new SeekbarWrapper(findViewById(R.id.servo2), Commands.SERVO_SECOND, oStreamR);
        servo_third = new SeekbarWrapper(findViewById(R.id.servo3), Commands.SERVO_THIRD, oStreamR);
        leftJoy = new JoystickWrapper(findViewById(R.id.leftJoy), oStreamR, findViewById(R.id.leftJoyInform), Commands.LEFT, this);
        rightJoy = new JoystickWrapper(findViewById(R.id.rightJoy), oStreamR, findViewById(R.id.rightJoyInform), Commands.RIGHT, this);
        final Switch leftSwitch = findViewById(R.id.leftChangeDir);
        leftSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            getPreferences(MODE_PRIVATE).edit().putBoolean(LEFT_CHANGE_DIR_KEY, isChecked).apply();
        });
        final boolean leftChange = getPreferences(MODE_PRIVATE).getBoolean(LEFT_CHANGE_DIR_KEY, false);
        final Switch rightSwitch = findViewById(R.id.rightChangeDir);
        rightSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            getPreferences(MODE_PRIVATE).edit().putBoolean(RIGHT_CHANGE_DIR_KEY, isChecked).apply();
        });
        final boolean rightChange = getPreferences(MODE_PRIVATE).getBoolean(RIGHT_CHANGE_DIR_KEY, false);
        if (leftChange) {
            leftJoy.changeSign();
            leftSwitch.toggle();
        }
        if (rightChange) {
            rightJoy.changeSign();
            rightSwitch.toggle();
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