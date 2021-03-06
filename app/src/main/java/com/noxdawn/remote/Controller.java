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
import com.noxdawn.remote.seekbar_listener_dacor.SeekbarCommandSendListenerDacor;
import com.noxdawn.remote.seekbar_listener_dacor.SeekbarInformListener;
import com.noxdawn.remote.seekbar_listener_dacor.SeekbarMappingListenerDacor;

import java.io.IOException;
import java.io.OutputStream;
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
    private static final String LEFT_THRESH_HOLD = "LEFT_THRESH_HOLD";
    private static final String LEFT_UPPER_LIMIT_KEY = "UPPER_LIMIT";
    private static final String RIGHT_THRESH_HOLD_KEY = "RIGHT_THRESH_HOLD";
    private static final String RIGHT_UPPER_LIMIT_KEY = "RIGHT_UPPER_LIMIT";
    
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
        SeekBar servo1 = findViewById(R.id.servo1);
        servo1.setOnSeekBarChangeListener(new SeekbarCommandSendListenerDacor(new SeekbarInformListener(findViewById(R.id.servo1_inform)), Commands.SERVO_FIRST, oStreamR));
        findViewById(R.id.servo1_common2).setOnClickListener(new ButtonResetSeekbarListener(servo1, 55));
        findViewById(R.id.servo1_common1).setOnClickListener(new ButtonResetSeekbarListener(servo1, 10));
        
        ((SeekBar) findViewById(R.id.servo2)).setOnSeekBarChangeListener(new SeekbarMappingListenerDacor(-180, 180, new SeekbarCommandSendListenerDacor(new SeekbarInformListener(findViewById(R.id.servo2_inform)), Commands.SERVO_SECOND, oStreamR)));
        findViewById(R.id.servo2r).setOnClickListener(new ButtonCommandSendListenerDacor(new ButtonResetSeekbarListener(findViewById(R.id.servo2), 180), "servo2r", oStreamR, this));
        findViewById(R.id.servo2f).setOnClickListener(new ButtonSeekbarChangeListener(findViewById(R.id.servo2), 90));
        findViewById(R.id.servo2b).setOnClickListener(new ButtonSeekbarChangeListener(findViewById(R.id.servo2), -90));
    
        ((SeekBar) findViewById(R.id.servo3)).setOnSeekBarChangeListener(new SeekbarMappingListenerDacor(-360, 360, new SeekbarCommandSendListenerDacor(new SeekbarInformListener(findViewById(R.id.servo3_inform)), Commands.SERVO_THIRD, oStreamR)));
        findViewById(R.id.servo3r).setOnClickListener(new ButtonCommandSendListenerDacor(new ButtonResetSeekbarListener(findViewById(R.id.servo3), 360), "servo3r", oStreamR, this));
        //start init left joystick
        BehaviorParameter<Integer> leftUpperLimit = new IntSeekbarParameter(LEFT_UPPER_LIMIT_KEY, this, 255, findViewById(R.id.leftUpperLimit));
        BehaviorParameter<Integer> leftThreshold = new IntSeekbarParameter(LEFT_THRESH_HOLD, this, 0, findViewById(R.id.leftThreshold));
        BehaviorParameter<Boolean> leftChangeDir = new BoolCheBarParameter(false, LEFT_CHANGE_DIR_KEY, this, findViewById(R.id.leftChangeDir));
        leftJoy = new JoystickWrapper(findViewById(R.id.leftJoy), oStreamR, findViewById(R.id.leftJoyInform), Commands.LEFT, this, leftChangeDir, leftThreshold, leftUpperLimit);
        //start init right joystick
        BehaviorParameter<Integer> rightUpperLimit = new IntSeekbarParameter(RIGHT_UPPER_LIMIT_KEY, this, 255, findViewById(R.id.rightUpperLimit));
        BehaviorParameter<Integer> rightThreshold = new IntSeekbarParameter(RIGHT_THRESH_HOLD_KEY, this, 0, findViewById(R.id.rightThreshold));
        BehaviorParameter<Boolean> rightChangeDir = new BoolCheBarParameter(false, RIGHT_CHANGE_DIR_KEY, this, findViewById(R.id.rightChangeDir));
        rightJoy = new JoystickWrapper(findViewById(R.id.rightJoy), oStreamR, findViewById(R.id.rightJoyInform), Commands.RIGHT, this, rightChangeDir, rightThreshold, rightUpperLimit);
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