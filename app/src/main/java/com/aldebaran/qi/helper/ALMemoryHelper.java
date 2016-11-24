package com.aldebaran.qi.helper;

import com.aldebaran.qi.AnyObject;
import com.aldebaran.qi.CallError;
import com.aldebaran.qi.Future;
import com.aldebaran.qi.Session;
import java.util.HashMap;
import java.util.Map;

public abstract class ALMemoryHelper
        extends ALProxy
{
    private HashMap<Long, AnyObject> subscribers;

    public ALMemoryHelper(Session session)
            throws Exception
    {
        super(session);
        this.subscribers = new HashMap();
    }

    public AnyObject subscriber(String eventName)
            throws CallError, InterruptedException
    {
        if (this.service == null) {
            throw new CallError();
        }
        return (AnyObject)this.service.call("subscriber", new Object[] { eventName }).get();
    }

    public long subscribeToEvent(String event, String signature, Object callback)
            throws Exception
    {
        AnyObject subscriber = subscriber(event);
        Long id = Long.valueOf(subscriber.connect("signal", signature, callback));
        this.subscribers.put(id, subscriber);
        return id.longValue();
    }

    public long subscribeToEvent(String event, EventCallback callback)
            throws Exception
    {
        return subscribeToEvent(event, "onEvent::(m)", callback);
    }

    public void unsubscribeToEvent(long eventID)
            throws InterruptedException, CallError
    {
        AnyObject subscriber = (AnyObject)this.subscribers.get(Long.valueOf(eventID));
        if (subscriber != null)
        {
            subscriber.disconnect(eventID);
            this.subscribers.remove(Long.valueOf(eventID));
        }
    }

    public void unsubscribeAllEvents()
            throws InterruptedException, CallError
    {
        for (Map.Entry<Long, AnyObject> entry : this.subscribers.entrySet())
        {
            ((AnyObject)entry.getValue()).disconnect(((Long)entry.getKey()).longValue());
            this.subscribers.remove(entry.getKey());
        }
    }
}
