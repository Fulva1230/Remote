package com.noxdawn.remote.behaviorparameter;

import android.app.Activity;
import android.content.Context;
import android.widget.SeekBar;

public class IntSeekbarParameter extends BehaviorParameter<Integer> {
    public IntSeekbarParameter(String key, Activity activity, Integer defaultValue, SeekBar seekBar) {
        super(defaultValue);
        seekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        saveValue(progress);
                        activity.getPreferences(Context.MODE_PRIVATE).edit().putInt(key, progress).apply();
                    }
                    
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    
                    }
                    
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    
                    }
                }
        );
        seekBar.setProgress(activity.getPreferences(Context.MODE_PRIVATE).getInt(key, defaultValue));
    }
}
