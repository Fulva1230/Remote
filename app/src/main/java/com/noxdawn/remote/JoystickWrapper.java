package com.noxdawn.remote;

import android.app.Activity;
import android.widget.TextView;
import io.github.controlwear.virtual.joystick.android.JoystickView;

import java.io.OutputStream;
import java.util.concurrent.atomic.AtomicReference;

public class JoystickWrapper {
    private final JoystickView joystick;
    private final TextView inform;
    private final CommandSender commandSender;
    private final Activity activity;
    
    public JoystickWrapper(JoystickView joystick, AtomicReference<OutputStream> oStreamR, TextView inform, String comString, Activity activity) {
        this.joystick = joystick;
        this.inform = inform;
        this.activity = activity;
        this.commandSender = new CommandSender(comString, oStreamR);
        joystick.setEnabled(true);
        joystick.setOnMoveListener(new OnMoveListener((short) 1), 100);
    }
    
    public void changeSign() {
        joystick.setOnMoveListener(new OnMoveListener((short) -1), 100);
    }
    
    private class OnMoveListener implements JoystickView.OnMoveListener {
        private short sign;
    
        public OnMoveListener(short sign) {
            this.sign = sign;
        }
        
        @Override
        public void onMove(int angle, int strength) {
            int arg = (int) (3 * (Math.sin(Math.toRadians(angle)) * strength));
            inform.setText(String.valueOf(arg));
            commandSender.sendCommand(joystick.getContext(), String.valueOf(arg * sign));
        }
    }
    
    
}
