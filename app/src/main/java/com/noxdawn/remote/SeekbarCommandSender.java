package com.noxdawn.remote;

import android.widget.SeekBar;
import android.widget.TextView;

import java.io.OutputStream;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class SeekbarCommandSender {
    private final SeekBar seekBar;
    private final CommandSender commandSender;
    private final Optional<TextView> inform;
    
    //TODO ImpOnSeekBar...
    public SeekbarCommandSender(SeekBar seekBar, String identifier, AtomicReference<OutputStream> oStreamR, Optional<TextView> inform) {
        this.seekBar = seekBar;
        this.inform = inform;
        this.commandSender = new CommandSender(identifier, oStreamR);
        seekBar.setOnSeekBarChangeListener(new ImpOnSeekBarChangeListener());
    }
    
    private class ImpOnSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            commandSender.sendCommand(seekBar.getContext(), String.valueOf(progress));
            inform.ifPresent(textView -> textView.setText(String.valueOf(progress)));
        }
        
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        
        }
        
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        
        }
    }
}
