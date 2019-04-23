package com.noxdawn.remote;

import android.widget.TextView;
import android.widget.Toast;
import io.github.controlwear.virtual.joystick.android.JoystickView;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Locale;

public class JoystickWrapper {
    private final JoystickView joystick;
    private final OutputStream outputStream;
    private final TextView inform;
    private final String comString;
    
    public JoystickWrapper(JoystickView joystick, OutputStream outputStream, TextView inform, String comString) {
        this.joystick = joystick;
        this.outputStream = outputStream;
        this.inform = inform;
        this.comString = comString;
        joystick.setEnabled(true);
        joystick.setOnMoveListener(new OnMoveListener());
    }
    
    
    private class OnMoveListener implements JoystickView.OnMoveListener {
        @Override
        public void onMove(int angle, int strength) {
            int arg = (int) (3 * (Math.sin(Math.toRadians(angle)) * strength));
            inform.setText(String.valueOf(arg));
            try {
                outputStream.write(String.format(Locale.getDefault(), "%s:%d;", comString, arg).getBytes());
                outputStream.flush();
            } catch (IOException e) {
                Toast message = Toast.makeText(joystick.getContext(), "fail to send message", Toast.LENGTH_SHORT);
                message.show();
                e.printStackTrace();
            }
        }
    }
}
