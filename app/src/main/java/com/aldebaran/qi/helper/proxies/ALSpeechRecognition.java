package com.aldebaran.qi.helper.proxies;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.Future;
import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.ALProxy;
import java.util.List;

public class ALSpeechRecognition extends ALProxy {
    public ALSpeechRecognition(Session session) throws Exception {
        super(session);
    }

    public String getLanguage() throws CallError, InterruptedException {
        return (String)call("getLanguage", new Object[0]).get();
    }

    public void setWordListAsVocabulary(List<String> vocabulary) throws CallError, InterruptedException {
        call("setWordListAsVocabulary", new Object[] { vocabulary }).get();
    }

    public void setAudioExpression(Boolean setOrNot) throws CallError, InterruptedException
    {
        call("setAudioExpression", new Object[] { setOrNot }).get();
    }

    public void pause(Boolean pause) throws CallError, InterruptedException
    {
        call("pause", new Object[] { pause }).get();
    }

    public void compile(String param1, String param2, String param3) throws CallError, InterruptedException {
        call("compile", new Object[] { param1, param2, param3 }).get();
    }

    public void setLanguage(String languageName) throws CallError, InterruptedException {
        call("setLanguage", new Object[] { languageName }).get();
    }

    public void removeContext(String contextName) throws CallError, InterruptedException {
        call("removeContext", new Object[] { contextName }).get();
    }

    public void removeAllContext() throws CallError, InterruptedException {
        call("removeAllContext", new Object[0]).get();
    }

    public void pushContexts() throws CallError, InterruptedException {
        call("pushContexts", new Object[0]).get();
    }

    public void popContexts() throws CallError, InterruptedException {
        call("popContexts", new Object[0]).get();
    }

    public Boolean saveContextSet(String saveName)
            throws CallError, InterruptedException
    {
        return (Boolean)call("saveContextSet", new Object[] { saveName }).get();
    }

    public void eraseContextSet(String saveName)
            throws CallError, InterruptedException
    {
        call("eraseContextSet", new Object[] { saveName }).get();
    }

    public void activateRule(String contextName, String ruleName)
            throws CallError, InterruptedException
    {
        call("activateRule", new Object[] { contextName, ruleName }).get();
    }

    public void deactivateRule(String contextName, String ruleName)
            throws CallError, InterruptedException
    {
        call("deactivateRule", new Object[] { contextName, ruleName }).get();
    }

    public void activateAllRules(String contextName)
            throws CallError, InterruptedException
    {
        call("activateAllRules", new Object[] { contextName }).get();
    }

    public void deactivateAllRules(String contextName)
            throws CallError, InterruptedException
    {
        call("deactivateAllRules", new Object[] { contextName }).get();
    }

    public void setContextParam(String contextName, String paramName, Float value)
            throws CallError, InterruptedException
    {
        call("setContextParam", new Object[] { contextName, paramName, value }).get();
    }

    public Float getContextParam(String contextName, String paramName)
            throws CallError, InterruptedException
    {
        return (Float)call("getContextParam", new Object[] { contextName, paramName }).get();
    }

    public void addWordListToSlot(String contextName, String slotName, List<String> wordList)
            throws CallError, InterruptedException
    {
        call("addWordListToSlot", new Object[] { contextName, slotName, wordList }).get();
    }

    public void removeWordListFromSlot(String contextName, String slotName)
            throws CallError, InterruptedException
    {
        call("removeWordListFromSlot", new Object[] { contextName, slotName }).get();
    }

    public List<String> getRules(String contextName, String typeName)
            throws CallError, InterruptedException
    {
        return (List)call("getRules", new Object[] { contextName, typeName }).get();
    }

    public List<String> getAvailableLanguages()
            throws CallError, InterruptedException
    {
        return (List)call("getAvailableLanguages", new Object[0]).get();
    }

    public void loadVocabulary(String vocabularyFile)
            throws CallError, InterruptedException
    {
        call("loadVocabulary", new Object[] { vocabularyFile }).get();
    }

    public void setParameter(String paramName, Float paramValue)
            throws CallError, InterruptedException
    {
        call("setParameter", new Object[] { paramName, paramValue }).get();
    }

    public Boolean getAudioExpression()
            throws CallError, InterruptedException
    {
        return (Boolean)call("getAudioExpression", new Object[0]).get();
    }

    public void setParameter(String paramName, Boolean paramValue)
            throws CallError, InterruptedException
    {
        call("setParameter", new Object[] { paramName, paramValue }).get();
    }

    public Float getParameter(String paramName)
            throws CallError, InterruptedException
    {
        return (Float)call("getParameter", new Object[] { paramName }).get();
    }

    public void setVocabulary(List<String> vocabulary, Boolean enabledWordSpotting)
            throws CallError, InterruptedException
    {
        call("setVocabulary", new Object[] { vocabulary, enabledWordSpotting }).get();
    }

    public void loadContextSet(String saveName)
            throws CallError, InterruptedException
    {
        call("loadContextSet", new Object[] { saveName }).get();
    }

    public void addContext(String pathToLCFFile, String contextName)
            throws CallError, InterruptedException
    {
        call("addContext", new Object[] { pathToLCFFile, contextName }).get();
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

    public void setVisualExpression(Boolean setOrNot)
            throws CallError, InterruptedException
    {
        call("setVisualExpression", new Object[] { setOrNot }).get();
    }

    public void setVisualExpressionMode(Integer mode)
            throws CallError, InterruptedException
    {
        call("setVisualExpressionMode", new Object[] { mode }).get();
    }
}
