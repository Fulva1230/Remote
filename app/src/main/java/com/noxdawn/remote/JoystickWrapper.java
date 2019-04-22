package com.noxdawn.remote;

import io.github.controlwear.virtual.joystick.android.JoystickView;

import java.io.OutputStream;

public class JoystickWrapper {
    private final JoystickView joystick;
    private final OutputStream outputStream;
    private final TextView imform;
    
    public JoystickWrapper(JoystickView joystick, OutputStream outputStream, TextView imform) {
        this.joystick = joystick;
        this.outputStream = outputStream;
        this.imform = imform;
        joystick.setEnabled(true);
    }
    
    
    private class OnMoveListener implements JoystickView.OnMoveListener {
        @Override
        public void onMove(int angle, int strength) {
        
        }
    }
}
