package com.noxdawn.remote;

import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

public class ButtonResetSeekbarListener implements Button.OnClickListener {
    private final SeekBar seekbar;
    
    public ButtonResetSeekbarListener(SeekBar seekbar) {
        this.seekbar = seekbar;
    }
    
    @Override
    public void onClick(View v) {
        seekbar.setProgress(0);
    }
}
