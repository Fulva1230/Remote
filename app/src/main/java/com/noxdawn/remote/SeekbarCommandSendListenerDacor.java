package com.noxdawn.remote;

import android.widget.SeekBar;

import java.io.OutputStream;
import java.util.concurrent.atomic.AtomicReference;

public class SeekbarCommandSendListenerDacor implements SeekBar.OnSeekBarChangeListener {
    private final SeekBar.OnSeekBarChangeListener listener;
    
    private final CommandSender commandSender;
    
    public SeekbarCommandSendListenerDacor(SeekBar.OnSeekBarChangeListener listener, String identifier, AtomicReference<OutputStream> oStreamR) {
        this.listener = listener;
        this.commandSender = new CommandSender(identifier, oStreamR);
    }
    
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        commandSender.sendCommand(seekBar.getContext(), String.valueOf(progress));
        listener.onProgressChanged(seekBar, progress, fromUser);
    }
    
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    
    }
    
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    
    }
}
