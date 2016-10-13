package com.aldebaran.qi;

import com.aldebaran.qi.AnyObject;
import com.aldebaran.qi.CallError;

public abstract class SignalCallback<T>
{
    public abstract void onSignal(T paramT)
            throws InterruptedException, CallError;

    public String getNaoqiType(Class<T> tClass)
    {
        if (tClass == String.class) {
            return "s";
        }
        if (tClass == Integer.class) {
            return "i";
        }
        if (tClass == Character.class) {
            return "c";
        }
        if (tClass == Void.class) {
            return "v";
        }
        if (tClass == AnyObject.class) {
            return "o";
        }
        if (tClass == Boolean.class) {
            return "b";
        }
        if (tClass == Float.class) {
            return "f";
        }
        if (tClass == Long.class) {
            return "l";
        }
        return "m";
    }
}
