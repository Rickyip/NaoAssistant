package com.aldebaran.qi.helper.proxies;

/**
 * Created by Yip on 13/10/2016.
 */

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.Future;
import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.ALProxy;
import java.util.List;

public class ALSoundDetection
        extends ALProxy
{
    public ALSoundDetection(Session session)
            throws Exception
    {
        super(session);
    }

    public Boolean isProcessing()
            throws CallError, InterruptedException
    {
        return (Boolean)call("isProcessing", new Object[0]).get();
    }

    public void setParameter(String parameter, Object value)
            throws CallError, InterruptedException
    {
        call("setParameter", new Object[] { parameter, value }).get();
    }

    public Boolean isStatsEnabled()
            throws CallError, InterruptedException
    {
        return (Boolean)call("isStatsEnabled", new Object[0]).get();
    }

    public void clearStats()
            throws CallError, InterruptedException
    {
        call("clearStats", new Object[0]).get();
    }

    public Boolean isTraceEnabled()
            throws CallError, InterruptedException
    {
        return (Boolean)call("isTraceEnabled", new Object[0]).get();
    }

    public void exit()
            throws CallError, InterruptedException
    {
        call("exit", new Object[0]).get();
    }

    public String version()
            throws CallError, InterruptedException
    {
        return (String)call("version", new Object[0]).get();
    }

    public Boolean ping()
            throws CallError, InterruptedException
    {
        return (Boolean)call("ping", new Object[0]).get();
    }

    public List<String> getMethodList()
            throws CallError, InterruptedException
    {
        return (List)call("getMethodList", new Object[0]).get();
    }

    public Object getMethodHelp(String methodName)
            throws CallError, InterruptedException
    {
        return call("getMethodHelp", new Object[] { methodName }).get();
    }

    public Object getModuleHelp()
            throws CallError, InterruptedException
    {
        return call("getModuleHelp", new Object[0]).get();
    }

    public Boolean wait(Integer id, Integer timeoutPeriod)
            throws CallError, InterruptedException
    {
        return (Boolean)call("wait", new Object[] { id, timeoutPeriod }).get();
    }

    public Boolean isRunning(Integer id)
            throws CallError, InterruptedException
    {
        return (Boolean)call("isRunning", new Object[] { id }).get();
    }

    public void stop(Integer id)
            throws CallError, InterruptedException
    {
        call("stop", new Object[] { id }).get();
    }

    public String getBrokerName()
            throws CallError, InterruptedException
    {
        return (String)call("getBrokerName", new Object[0]).get();
    }

    public String getUsage(String name)
            throws CallError, InterruptedException
    {
        return (String)call("getUsage", new Object[] { name }).get();
    }

    public void subscribe(String name, Integer period, Float precision)
            throws CallError, InterruptedException
    {
        call("subscribe", new Object[] { name, period, precision }).get();
    }

    public void subscribe(String name)
            throws CallError, InterruptedException
    {
        call("subscribe", new Object[] { name }).get();
    }

    public void unsubscribe(String name)
            throws CallError, InterruptedException
    {
        call("unsubscribe", new Object[] { name }).get();
    }

    public void updatePeriod(String name, Integer period)
            throws CallError, InterruptedException
    {
        call("updatePeriod", new Object[] { name, period }).get();
    }

    public void updatePrecision(String name, Float precision)
            throws CallError, InterruptedException
    {
        call("updatePrecision", new Object[] { name, precision }).get();
    }

    public Integer getCurrentPeriod()
            throws CallError, InterruptedException
    {
        return (Integer)call("getCurrentPeriod", new Object[0]).get();
    }

    public Float getCurrentPrecision()
            throws CallError, InterruptedException
    {
        return (Float)call("getCurrentPrecision", new Object[0]).get();
    }

    public Integer getMyPeriod(String name)
            throws CallError, InterruptedException
    {
        return (Integer)call("getMyPeriod", new Object[] { name }).get();
    }

    public Float getMyPrecision(String name)
            throws CallError, InterruptedException
    {
        return (Float)call("getMyPrecision", new Object[] { name }).get();
    }

    public Object getSubscribersInfo()
            throws CallError, InterruptedException
    {
        return call("getSubscribersInfo", new Object[0]).get();
    }

    public List<String> getOutputNames()
            throws CallError, InterruptedException
    {
        return (List)call("getOutputNames", new Object[0]).get();
    }

    public List<String> getEventList()
            throws CallError, InterruptedException
    {
        return (List)call("getEventList", new Object[0]).get();
    }

    public List<String> getMemoryKeyList()
            throws CallError, InterruptedException
    {
        return (List)call("getMemoryKeyList", new Object[0]).get();
    }

    public Boolean isPaused()
            throws CallError, InterruptedException
    {
        return (Boolean)call("isPaused", new Object[0]).get();
    }

    public void pause(Boolean status)
            throws CallError, InterruptedException
    {
        call("pause", new Object[] { status }).get();
    }
}
