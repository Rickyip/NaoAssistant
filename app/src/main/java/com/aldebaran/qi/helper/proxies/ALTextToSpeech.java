package com.aldebaran.qi.helper.proxies;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.ALProxy;

import java.util.List;

public class ALTextToSpeech
        extends ALProxy
{
    public ALTextToSpeech(Session session)
            throws Exception
    {
        super(session);
    }

    public void loadVoicePreference(String pPreferenceName)
            throws CallError, InterruptedException
    {
        call("loadVoicePreference", new Object[] { pPreferenceName }).get();
    }

    public List<String> getAvailableVoices()
            throws CallError, InterruptedException
    {
        return (List)call("getAvailableVoices", new Object[0]).get();
    }

    public Float getVolume()
            throws CallError, InterruptedException
    {
        return (Float)call("getVolume", new Object[0]).get();
    }

    public String locale()
            throws CallError, InterruptedException
    {
        return (String)call("locale", new Object[0]).get();
    }

    public void setLanguageDefaultVoice(String Language, String Voice)
            throws CallError, InterruptedException
    {
        call("setLanguageDefaultVoice", new Object[] { Language, Voice }).get();
    }

    public void setVolume(Float volume)
            throws CallError, InterruptedException
    {
        call("setVolume", new Object[] { volume }).get();
    }

    public void enableNotifications()
            throws CallError, InterruptedException
    {
        call("enableNotifications", new Object[0]).get();
    }

    public void disableNotifications()
            throws CallError, InterruptedException
    {
        call("disableNotifications", new Object[0]).get();
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

    public void say(String stringToSay)
            throws CallError, InterruptedException
    {
        call("say", new Object[] { stringToSay }).get();
    }

    public void say(String stringToSay, String language)
            throws CallError, InterruptedException
    {
        call("say", new Object[] { stringToSay, language }).get();
    }

    public void sayToFile(String pStringToSay, String pFileName)
            throws CallError, InterruptedException
    {
        call("sayToFile", new Object[] { pStringToSay, pFileName }).get();
    }

    public void sayToFileAndPlay(String pStringToSay)
            throws CallError, InterruptedException
    {
        call("sayToFileAndPlay", new Object[] { pStringToSay }).get();
    }

    public void stopAll()
            throws CallError, InterruptedException
    {
        call("stopAll", new Object[0]).get();
    }

    public void setLanguage(String pLanguage)
            throws CallError, InterruptedException
    {
        call("setLanguage", new Object[] { pLanguage }).get();
    }

    public String getLanguage()
            throws CallError, InterruptedException
    {
        return (String)call("getLanguage", new Object[0]).get();
    }

    public String getLanguageEncoding(String pLanguage)
            throws CallError, InterruptedException
    {
        return (String)call("getLanguageEncoding", new Object[] { pLanguage }).get();
    }

    public List<String> getAvailableLanguages()
            throws CallError, InterruptedException
    {
        return (List)call("getAvailableLanguages", new Object[0]).get();
    }

    public List<String> getSupportedLanguages()
            throws CallError, InterruptedException
    {
        return (List)call("getSupportedLanguages", new Object[0]).get();
    }

    public void resetSpeed()
            throws CallError, InterruptedException
    {
        call("resetSpeed", new Object[0]).get();
    }

    public void setParameter(String pEffectName, Float pEffectValue)
            throws CallError, InterruptedException
    {
        call("setParameter", new Object[] { pEffectName, pEffectValue }).get();
    }

    public Float getParameter(String pParameterName)
            throws CallError, InterruptedException
    {
        return (Float)call("getParameter", new Object[] { pParameterName }).get();
    }

    public void setVoice(String pVoiceID)
            throws CallError, InterruptedException
    {
        call("setVoice", new Object[] { pVoiceID }).get();
    }

    public String getVoice()
            throws CallError, InterruptedException
    {
        return (String)call("getVoice", new Object[0]).get();
    }
}
