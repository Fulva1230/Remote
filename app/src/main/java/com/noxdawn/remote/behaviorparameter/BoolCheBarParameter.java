package com.noxdawn.remote.behaviorparameter;

import android.app.Activity;
import android.content.Context;
import android.widget.Switch;

public class BoolCheBarParameter extends BehaviorParameter<Boolean> {
    public BoolCheBarParameter(Boolean defaultValue, String key, Activity activity, Switch aswitch) {
        super(defaultValue);
        aswitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            activity.getPreferences(Context.MODE_PRIVATE).edit().putBoolean(key, isChecked).apply();
            saveValue(isChecked);
        });
        if (activity.getPreferences(Context.MODE_PRIVATE).getBoolean(key, false)) {
            aswitch.toggle();
        }
    }
}
