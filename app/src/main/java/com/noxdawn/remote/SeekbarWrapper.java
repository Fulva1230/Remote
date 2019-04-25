package com.noxdawn.remote;

import android.widget.SeekBar;

import java.io.OutputStream;
import java.util.concurrent.atomic.AtomicReference;

public class SeekbarWrapper {
    private final SeekBar seekBar;
    private final CommandSender commandSender;
    
    //TODO ImpOnSeekBar...
    public SeekbarWrapper(SeekBar seekBar, String identifier, AtomicReference<OutputStream> oStreamR) {
        this.seekBar = seekBar;
        this.commandSender = new CommandSender(identifier, oStreamR);
        seekBar.setOnSeekBarChangeListener(new ImpOnSeekBarChangeListener());
    }
    
    private class ImpOnSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            commandSender.sendCommand(seekBar.getContext(), String.valueOf(progress));
        }
        
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        
        }
        
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        
        }
    }
}
