package com.aldebaran.qi.helper.proxies;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.Future;
import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.ALProxy;
import java.util.List;

public class ALAudioRecorder
        extends ALProxy
{
    public ALAudioRecorder(Session session)
            throws Exception
    {
        super(session);
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

    public void startMicrophonesRecording(String filename, String type, Integer samplerate, Object channels)
            throws CallError, InterruptedException
    {
        call("startMicrophonesRecording", new Object[] { filename, type, samplerate, channels }).get();
    }

    public void stopMicrophonesRecording()
            throws CallError, InterruptedException
    {
        call("stopMicrophonesRecording", new Object[0]).get();
    }
}
