package com.noxdawn.remote;

import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

public class ButtonResetSeekbarListener implements Button.OnClickListener {
    private final SeekBar seekbar;
    private final int resetValue;
    
    public ButtonResetSeekbarListener(SeekBar seekbar, int resetValue) {
        this.seekbar = seekbar;
        this.resetValue = resetValue;
    }
    
    @Override
    public void onClick(View v) {
        seekbar.setProgress(resetValue);
    }
}
