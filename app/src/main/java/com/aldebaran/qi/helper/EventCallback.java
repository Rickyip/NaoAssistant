package com.aldebaran.qi.helper;

import com.aldebaran.qi.CallError;

public abstract class EventCallback<T>
{
    public abstract void onEvent(T paramT)
            throws InterruptedException, CallError;
}
