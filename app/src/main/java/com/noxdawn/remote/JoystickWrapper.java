package com.noxdawn.remote;

import android.app.Activity;
import android.widget.TextView;
import com.noxdawn.remote.behaviorparameter.BehaviorParameter;
import io.github.controlwear.virtual.joystick.android.JoystickView;

import java.io.OutputStream;
import java.util.concurrent.atomic.AtomicReference;

import static java.lang.StrictMath.abs;

public class JoystickWrapper {
    private final JoystickView joystick;
    private final TextView inform;
    private final CommandSender commandSender;
    private final Activity activity;
    
    public JoystickWrapper(JoystickView joystick, AtomicReference<OutputStream> oStreamR, TextView inform, String comString, Activity activity, BehaviorParameter<Boolean> sign, BehaviorParameter<Integer> threshHold) {
        this.joystick = joystick;
        this.inform = inform;
        this.activity = activity;
        this.commandSender = new CommandSender(comString, oStreamR);
        joystick.setEnabled(true);
        joystick.setOnMoveListener(new OnMoveListener(sign, threshHold), 100);
    }
    
    private class OnMoveListener implements JoystickView.OnMoveListener {
        private final BehaviorParameter<Boolean> sign;
        private final BehaviorParameter<Integer> threshHold;
    
        OnMoveListener(BehaviorParameter<Boolean> sign, BehaviorParameter<Integer> threshHold) {
            this.sign = sign;
            this.threshHold = threshHold;
        }
        
        @Override
        public void onMove(int angle, int strength) {
            int arg;
            if (strength < 10) {
                arg = 0;
            } else {
                arg = (int) (((angle > 180) ? -1 : 1) * ((300 - threshHold.getValue()) * (abs(Math.sin(Math.toRadians(angle)) * strength / 100d))) + threshHold.getValue());
            }
            inform.setText(String.valueOf(arg));
            commandSender.sendCommand(joystick.getContext(), String.valueOf(sign.getValue() ? -arg : arg));
        }
    }
    
    
}
