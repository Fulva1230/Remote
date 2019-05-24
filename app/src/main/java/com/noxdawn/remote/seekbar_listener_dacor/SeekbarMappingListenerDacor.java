package com.noxdawn.remote.seekbar_listener_dacor;

import android.widget.SeekBar;

public class SeekbarMappingListenerDacor implements SeekBar.OnSeekBarChangeListener {
    private final int min;
    private final int max;
    private final SeekBar.OnSeekBarChangeListener listener;
    
    public SeekbarMappingListenerDacor(int min, int max, SeekBar.OnSeekBarChangeListener listener) {
        this.min = min;
        this.max = max;
        this.listener = listener;
    }
    
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        int nProgress = min + (max - min) * (progress - seekBar.getMin()) / (seekBar.getMax() - seekBar.getMin());
        listener.onProgressChanged(seekBar, nProgress, fromUser);
    }
    
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    
    }
    
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    
    }
}
