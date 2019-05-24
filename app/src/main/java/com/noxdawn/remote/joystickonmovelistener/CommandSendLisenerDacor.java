package com.noxdawn.remote.joystickonmovelistener;

import com.noxdawn.remote.CommandSender;
import io.github.controlwear.virtual.joystick.android.JoystickView;

import java.io.OutputStream;
import java.util.concurrent.atomic.AtomicReference;

public class CommandSendLisenerDacor implements JoystickView.OnMoveListener {
    private final CommandSender commandSender;
    private final JoystickView.OnMoveListener listener;
    
    public CommandSendLisenerDacor(JoystickView.OnMoveListener listener, String ident, AtomicReference<OutputStream> oStreamR) {
        this.listener = listener;
        commandSender = new CommandSender(ident, oStreamR);
    }
    
    @Override
    public void onMove(int angle, int strength) {
    }
}
