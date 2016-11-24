package com.aldebaran.qi.helper.proxies;

import com.aldebaran.qi.AnyObject;
import com.aldebaran.qi.CallError;
import com.aldebaran.qi.Future;
import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.ALMemoryHelper;
import java.util.List;
import java.util.Map;

public class ALMemory
        extends ALMemoryHelper
{
    public ALMemory(Session session)
            throws Exception
    {
        super(session);
    }

    public void insertData(String key, String value)
            throws CallError, InterruptedException
    {
        call("insertData", new Object[] { key, value }).get();
    }

    public void removeData(String key)
            throws CallError, InterruptedException
    {
        call("removeData", new Object[] { key }).get();
    }

    public List<String> getSubscribers(String name)
            throws CallError, InterruptedException
    {
        return (List)call("getSubscribers", new Object[] { name }).get();
    }

    public void removeMicroEvent(String name)
            throws CallError, InterruptedException
    {
        call("removeMicroEvent", new Object[] { name }).get();
    }

    public void subscribeToEvent(String name, String callbackModule, String callbackMethod)
            throws CallError, InterruptedException
    {
        call("subscribeToEvent", new Object[] { name, callbackModule, callbackMethod }).get();
    }

    public void insertData(String key, Integer value)
            throws CallError, InterruptedException
    {
        call("insertData", new Object[] { key, value }).get();
    }

    public void subscribeToMicroEvent(String name, String callbackModule, String callbackMessage, String callbackMethod)
            throws CallError, InterruptedException
    {
        call("subscribeToMicroEvent", new Object[] { name, callbackModule, callbackMessage, callbackMethod }).get();
    }

    public void unregisterModuleReference(String moduleName)
            throws CallError, InterruptedException
    {
        call("unregisterModuleReference", new Object[] { moduleName }).get();
    }

    public void unsubscribeToEvent(String name, String callbackModule)
            throws CallError, InterruptedException
    {
        call("unsubscribeToEvent", new Object[] { name, callbackModule }).get();
    }

    public void unsubscribeToMicroEvent(String name, String callbackModule)
            throws CallError, InterruptedException
    {
        call("unsubscribeToMicroEvent", new Object[] { name, callbackModule }).get();
    }

    public void insertData(String key, Float value)
            throws CallError, InterruptedException
    {
        call("insertData", new Object[] { key, value }).get();
    }

    public void setDescription(String name, String description)
            throws CallError, InterruptedException
    {
        call("setDescription", new Object[] { name, description }).get();
    }

    public Object getDescriptionList(List<String> keylist)
            throws CallError, InterruptedException
    {
        return call("getDescriptionList", new Object[] { keylist }).get();
    }

    public void addMapping(String service, String signal, String event)
            throws CallError, InterruptedException
    {
        call("addMapping", new Object[] { service, signal, event }).get();
    }

    public void addMapping(String service, Map<String, String> signalEvent)
            throws CallError, InterruptedException
    {
        call("addMapping", new Object[] { service, signalEvent }).get();
    }

    public void insertData(String key, Object data)
            throws CallError, InterruptedException
    {
        call("insertData", new Object[] { key, data }).get();
    }

    public void insertListData(Object list)
            throws CallError, InterruptedException
    {
        call("insertListData", new Object[] { list }).get();
    }

    public String getType(String key)
            throws CallError, InterruptedException
    {
        return (String)call("getType", new Object[] { key }).get();
    }

    public void raiseEvent(String name, Object value)
            throws CallError, InterruptedException
    {
        call("raiseEvent", new Object[] { name, value }).get();
    }

    public void raiseMicroEvent(String name, Object value)
            throws CallError, InterruptedException
    {
        call("raiseMicroEvent", new Object[] { name, value }).get();
    }

    public void removeEvent(String name)
            throws CallError, InterruptedException
    {
        call("removeEvent", new Object[] { name }).get();
    }

    public void subscribeToEvent(String name, String callbackModule, String callbackMessage, String callbacMethod)
            throws CallError, InterruptedException
    {
        call("subscribeToEvent", new Object[] { name, callbackModule, callbackMessage, callbacMethod }).get();
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

    public void declareEvent(String eventName)
            throws CallError, InterruptedException
    {
        call("declareEvent", new Object[] { eventName }).get();
    }

    public void declareEvent(String eventName, String extractorName)
            throws CallError, InterruptedException
    {
        call("declareEvent", new Object[] { eventName, extractorName }).get();
    }

    public Object getData(String key)
            throws CallError, InterruptedException
    {
        return call("getData", new Object[] { key }).get();
    }

    public Object getData(String key, Integer deprecatedParameter)
            throws CallError, InterruptedException
    {
        return call("getData", new Object[] { key, deprecatedParameter }).get();
    }

    public AnyObject subscriber(String eventName)
            throws CallError, InterruptedException
    {
        return (AnyObject)call("subscriber", new Object[] { eventName }).get();
    }

    public Object getTimestamp(String key)
            throws CallError, InterruptedException
    {
        return call("getTimestamp", new Object[] { key }).get();
    }

    public Object getEventHistory(String key)
            throws CallError, InterruptedException
    {
        return call("getEventHistory", new Object[] { key }).get();
    }

    public List<String> getDataList(String filter)
            throws CallError, InterruptedException
    {
        return (List)call("getDataList", new Object[] { filter }).get();
    }

    public List<String> getDataListName()
            throws CallError, InterruptedException
    {
        return (List)call("getDataListName", new Object[0]).get();
    }

    public Object getDataOnChange(String key, Integer deprecatedParameter)
            throws CallError, InterruptedException
    {
        return call("getDataOnChange", new Object[] { key, deprecatedParameter }).get();
    }

    public Object getDataPtr(String key)
            throws CallError, InterruptedException
    {
        return call("getDataPtr", new Object[] { key }).get();
    }

    public List<String> getEventList()
            throws CallError, InterruptedException
    {
        return (List)call("getEventList", new Object[0]).get();
    }

    public List<String> getExtractorEvent(String extractorName)
            throws CallError, InterruptedException
    {
        return (List)call("getExtractorEvent", new Object[] { extractorName }).get();
    }

    public Object getListData(Object keyList)
            throws CallError, InterruptedException
    {
        return call("getListData", new Object[] { keyList }).get();
    }

    public List<String> getMicroEventList()
            throws CallError, InterruptedException
    {
        return (List)call("getMicroEventList", new Object[0]).get();
    }
}
