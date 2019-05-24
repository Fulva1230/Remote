package com.noxdawn.remote;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.noxdawn.remote.behaviorparameter.BehaviorParameter;
import com.noxdawn.remote.behaviorparameter.BoolCheBarParameter;
import com.noxdawn.remote.behaviorparameter.IntSeekbarParameter;
import com.noxdawn.remote.btconnect.OStreamFetcher;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static android.widget.Toast.LENGTH_SHORT;
import static com.noxdawn.remote.btconnect.BtItemAdapter.BLUETOOTH_ADDRESS;
import static com.noxdawn.remote.btconnect.BtItemAdapter.BLUETOOTH_NAME;

public class Controller extends AppCompatActivity {
    
    private JoystickWrapper leftJoy;
    private JoystickWrapper rightJoy;
    private SeekbarCommandSender servo_first;
    private SeekbarCommandSender servo_second;
    private SeekbarCommandSender servo_third;
    private OStreamFetcher bleStrFet;
    private static final String LEFT_CHANGE_DIR_KEY = "LEFT_CHANGE";
    private static final String RIGHT_CHANGE_DIR_KEY = "RIGHT_CHANGE";
    private static final String THRESH_HOLD = "THRESH_HOLD";
    private static final String UPPER_LIMIT_KEY = "UPPER_LIMIT";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        // getSupportActionBar().hide();
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
        servo_first = new SeekbarCommandSender(findViewById(R.id.servo1), Commands.SERVO_FIRST, oStreamR, Optional.of(findViewById(R.id.servo1_inform)));
        ((SeekBar) findViewById(R.id.servo2)).setOnSeekBarChangeListener(new SeekbarMappingListenerDacor(-180, 180, new SeekbarCommandSendListenerDacor(new SeekbarInformListener(findViewById(R.id.servo2_inform)), Commands.SERVO_SECOND, oStreamR)));
        findViewById(R.id.servo2r).setOnClickListener(new ButtonCommandSendListenerDacor(new ButtonResetSeekbarListener(findViewById(R.id.servo2), 180), "servo2r", oStreamR, this));
        findViewById(R.id.servo2f).setOnClickListener(new ButtonSeekbarChangeListener(findViewById(R.id.servo2), 90));
        findViewById(R.id.servo2b).setOnClickListener(new ButtonSeekbarChangeListener(findViewById(R.id.servo2), -90));
        ((SeekBar) findViewById(R.id.servo3)).setOnSeekBarChangeListener(new SeekbarMappingListenerDacor(-360, 360, new SeekbarCommandSendListenerDacor(new SeekbarInformListener(findViewById(R.id.servo3_inform)), Commands.SERVO_THIRD, oStreamR)));
        findViewById(R.id.servo3r).setOnClickListener(new ButtonCommandSendListenerDacor(new ButtonResetSeekbarListener(findViewById(R.id.servo3), 360), "servo3r", oStreamR, this));
        BehaviorParameter<Integer> upperLimit = new IntSeekbarParameter(UPPER_LIMIT_KEY, this, 255, findViewById(R.id.upperLimit));
        BehaviorParameter<Integer> threshHold = new IntSeekbarParameter(THRESH_HOLD, this, 0, findViewById(R.id.threshHold));
        BehaviorParameter<Boolean> leftChangeDir = new BoolCheBarParameter(false, LEFT_CHANGE_DIR_KEY, this, findViewById(R.id.leftChangeDir));
        leftJoy = new JoystickWrapper(findViewById(R.id.leftJoy), oStreamR, findViewById(R.id.leftJoyInform), Commands.LEFT, this, leftChangeDir, threshHold, upperLimit);
        BehaviorParameter<Boolean> rightChangeDir = new BoolCheBarParameter(false, RIGHT_CHANGE_DIR_KEY, this, findViewById(R.id.rightChangeDir));
        rightJoy = new JoystickWrapper(findViewById(R.id.rightJoy), oStreamR, findViewById(R.id.rightJoyInform), Commands.RIGHT, this, rightChangeDir, threshHold, upperLimit);
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