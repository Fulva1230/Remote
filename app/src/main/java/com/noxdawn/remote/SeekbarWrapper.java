package com.noxdawn.remote;

import android.widget.SeekBar;

import java.io.OutputStream;

public class SeekbarWrapper {
    private final SeekBar seekBar;
    private final CommandSender commandSender;
    
    //TODO ImpOnSeekBar...
    public SeekbarWrapper(SeekBar seekBar, String identifier, OutputStream oStream) {
        this.seekBar = seekBar;
        this.commandSender = new CommandSender(identifier, oStream);
        seekBar.setOnSeekBarChangeListener(new ImpOnSeekBarChangeListener());
    }
    
    private class ImpOnSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        
        }
        
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        
        }
        
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        
        }
    }
}
