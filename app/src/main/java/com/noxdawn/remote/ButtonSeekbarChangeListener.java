package com.noxdawn.remote;

import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

public class ButtonSeekbarChangeListener implements Button.OnClickListener {
    private final SeekBar seekBar;
    private final int incre;
    
    public ButtonSeekbarChangeListener(SeekBar seekBar, int incre) {
        this.seekBar = seekBar;
        this.incre = incre;
    }
    
    @Override
    public void onClick(View v) {
        seekBar.setProgress(seekBar.getProgress() + incre);
    }
}
