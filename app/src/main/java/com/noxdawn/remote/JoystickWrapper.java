package com.noxdawn.remote;

import android.widget.TextView;
import io.github.controlwear.virtual.joystick.android.JoystickView;

import java.io.OutputStream;

public class JoystickWrapper {
    private final JoystickView joystick;
    private final TextView inform;
    private final CommandSender commandSender;
    
    public JoystickWrapper(JoystickView joystick, OutputStream outputStream, TextView inform, String comString) {
        this.joystick = joystick;
        this.inform = inform;
        this.commandSender = new CommandSender(comString, outputStream);
        joystick.setEnabled(true);
        joystick.setOnMoveListener(new OnMoveListener(), 100);
    }
    
    
    private class OnMoveListener implements JoystickView.OnMoveListener {
        @Override
        public void onMove(int angle, int strength) {
            int arg = (int) (3 * (Math.sin(Math.toRadians(angle)) * strength));
            inform.setText(String.valueOf(arg));
            commandSender.sendCommand(joystick.getContext(), String.valueOf(arg));
        }
    }
}
