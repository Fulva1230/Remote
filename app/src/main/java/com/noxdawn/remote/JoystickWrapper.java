package com.noxdawn.remote;

import android.widget.TextView;
import io.github.controlwear.virtual.joystick.android.JoystickView;

import java.io.OutputStream;

public class JoystickWrapper {
    private final JoystickView joystick;
    private final OutputStream outputStream;
    private final TextView inform;
    
    public JoystickWrapper(JoystickView joystick, OutputStream outputStream, TextView inform) {
        this.joystick = joystick;
        this.outputStream = outputStream;
        this.inform = inform;
        joystick.setEnabled(true);
        joystick.setOnMoveListener(new OnMoveListener());
    }
    
    
    private class OnMoveListener implements JoystickView.OnMoveListener {
        @Override
        public void onMove(int angle, int strength) {
            inform.setText(String.valueOf(Math.cos(Math.toRadians(angle)) * strength));
        }
    }
}
