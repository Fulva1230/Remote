package com.noxdawn.remote;

import android.widget.SeekBar;
import android.widget.TextView;

public class SeekbarInformListener implements SeekBar.OnSeekBarChangeListener {
    private final TextView inform;
    
    public SeekbarInformListener(TextView inform) {
        this.inform = inform;
    }
    
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        inform.setText(String.valueOf(progress));
        
    }
    
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    
    }
    
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    
    }
}
