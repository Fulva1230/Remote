package com.noxdawn.remote;

import android.app.Activity;
import android.view.View;
import android.widget.Button;

import java.io.OutputStream;
import java.util.concurrent.atomic.AtomicReference;

public class ButtonCommandSendListenerDacor implements Button.OnClickListener {
    private final Button.OnClickListener listener;
    private final CommandSender commandSender;
    private final Activity activity;
    
    public ButtonCommandSendListenerDacor(Button.OnClickListener listener, String ident, AtomicReference<OutputStream> oStreamR, Activity activity) {
        this.listener = listener;
        this.activity = activity;
        this.commandSender = new CommandSender(ident, oStreamR);
    }
    
    @Override
    public void onClick(View v) {
        listener.onClick(v);
        commandSender.sendCommand(activity);
    }
}
