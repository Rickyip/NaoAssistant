package com.aldebaran.qi.helper;

import com.aldebaran.qi.AnyObject;
import com.aldebaran.qi.CallError;
import com.aldebaran.qi.Future;
import com.aldebaran.qi.Session;
import com.aldebaran.qi.SignalCallback;

public abstract class ALProxy
{
    protected AnyObject service;
    protected String name;

    public ALProxy() {}

    public ALProxy(Session session)
            throws Exception
    {
        this.name = getClass().getSimpleName();
        this.service = session.service(this.name);
    }

    public ALProxy(Session session, String serviceName)
            throws Exception
    {
        this.name = serviceName;
        this.service = session.service(serviceName);
    }

    public long connect(String signal, String signature, Object callback)
            throws Exception
    {
        if (this.service == null) {
            throw new CallError();
        }
        return this.service.connect(signal, signature, callback);
    }

    public long connect(String signal, Class<?> theClass, SignalCallback callback)
            throws Exception
    {
        if (this.service == null) {
            throw new CallError();
        }
        return this.service.connect(signal, "onSignal::(" + callback.getNaoqiType(theClass) + ")", callback);
    }

    public void disconnect(long eventID)
            throws InterruptedException, CallError
    {
        if (this.service == null) {
            throw new CallError();
        }
        this.service.disconnect(eventID);
    }

    public <T> Future<T> call(String method, Object... args)
            throws CallError
    {
        if (this.service == null) {
            throw new CallError();
        }
        return this.service.call(method, args);
    }

    public AnyObject getService()
    {
        return this.service;
    }

    public void setService(AnyObject service)
    {
        this.service = service;
    }

    public boolean isProxyReady()
    {
        return this.service != null;
    }
}
