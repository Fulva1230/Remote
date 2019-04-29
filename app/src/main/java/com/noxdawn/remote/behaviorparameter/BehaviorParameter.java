package com.noxdawn.remote.behaviorparameter;

public class BehaviorParameter<T> {
    private T parameter;
    
    public BehaviorParameter(T defaultValue) {
        this.parameter = defaultValue;
    }
    
    public T getValue() {
        return parameter;
    }
    
    protected void saveValue(T newValue) {
        parameter = newValue;
    }
}
