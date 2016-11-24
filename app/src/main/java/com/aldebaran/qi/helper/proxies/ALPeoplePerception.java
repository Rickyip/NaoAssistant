package com.aldebaran.qi.helper.proxies;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.Future;
import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.ALProxy;
import java.util.List;

public class ALPeoplePerception
        extends ALProxy
{
    public ALPeoplePerception(Session session)
            throws Exception
    {
        super(session);
    }

    public Float getTimeBeforePersonDisappears()
            throws CallError, InterruptedException
    {
        return (Float)call("getTimeBeforePersonDisappears", new Object[0]).get();
    }

    public Boolean isMovementDetectionEnabled()
            throws CallError, InterruptedException
    {
        return (Boolean)call("isMovementDetectionEnabled", new Object[0]).get();
    }

    public Boolean isProcessing()
            throws CallError, InterruptedException
    {
        return (Boolean)call("isProcessing", new Object[0]).get();
    }

    public void setFastModeEnabled(Boolean enable)
            throws CallError, InterruptedException
    {
        call("setFastModeEnabled", new Object[] { enable }).get();
    }

    public void setGraphicalDisplayEnabled(Boolean enable)
            throws CallError, InterruptedException
    {
        call("setGraphicalDisplayEnabled", new Object[] { enable }).get();
    }

    public Float getMinimumBodyHeight()
            throws CallError, InterruptedException
    {
        return (Float)call("getMinimumBodyHeight", new Object[0]).get();
    }

    public void setMinimumBodyHeight(Float height)
            throws CallError, InterruptedException
    {
        call("setMinimumBodyHeight", new Object[] { height }).get();
    }

    public void setMaximumBodyHeight(Float height)
            throws CallError, InterruptedException
    {
        call("setMaximumBodyHeight", new Object[] { height }).get();
    }

    public void setMovementDetectionEnabled(Boolean enable)
            throws CallError, InterruptedException
    {
        call("setMovementDetectionEnabled", new Object[] { enable }).get();
    }

    public void setTimeBeforePersonDisappears(Float seconds)
            throws CallError, InterruptedException
    {
        call("setTimeBeforePersonDisappears", new Object[] { seconds }).get();
    }

    public void setTimeBeforeVisiblePersonDisappears(Float seconds)
            throws CallError, InterruptedException
    {
        call("setTimeBeforeVisiblePersonDisappears", new Object[] { seconds }).get();
    }

    public Float getMaximumBodyHeight()
            throws CallError, InterruptedException
    {
        return (Float)call("getMaximumBodyHeight", new Object[0]).get();
    }

    public Float getTimeBeforeVisiblePersonDisappears()
            throws CallError, InterruptedException
    {
        return (Float)call("getTimeBeforeVisiblePersonDisappears", new Object[0]).get();
    }

    public void setFaceDetectionEnabled(Boolean enable)
            throws CallError, InterruptedException
    {
        call("setFaceDetectionEnabled", new Object[] { enable }).get();
    }

    public Boolean isFaceDetectionEnabled()
            throws CallError, InterruptedException
    {
        return (Boolean)call("isFaceDetectionEnabled", new Object[0]).get();
    }

    public Float getMaximumDetectionRange()
            throws CallError, InterruptedException
    {
        return (Float)call("getMaximumDetectionRange", new Object[0]).get();
    }

    public Boolean isFastModeEnabled()
            throws CallError, InterruptedException
    {
        return (Boolean)call("isFastModeEnabled", new Object[0]).get();
    }

    public Boolean isGraphicalDisplayEnabled()
            throws CallError, InterruptedException
    {
        return (Boolean)call("isGraphicalDisplayEnabled", new Object[0]).get();
    }

    public void resetPopulation()
            throws CallError, InterruptedException
    {
        call("resetPopulation", new Object[0]).get();
    }

    public void setMaximumDetectionRange(Float range)
            throws CallError, InterruptedException
    {
        call("setMaximumDetectionRange", new Object[] { range }).get();
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
