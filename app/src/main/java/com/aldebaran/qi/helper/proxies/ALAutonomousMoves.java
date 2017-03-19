package com.aldebaran.qi.helper.proxies;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.Future;
import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.ALProxy;
import java.util.List;

public class ALAutonomousMoves
        extends ALProxy
{
    private AsyncALAutonomousMoves asyncProxy;

    public ALAutonomousMoves(Session session)
            throws Exception
    {
        super(session);
        this.asyncProxy = new AsyncALAutonomousMoves();
        this.asyncProxy.setService(getService());
    }

    public AsyncALAutonomousMoves async()
    {
        return this.asyncProxy;
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

    public void startSmallDisplacements()
            throws CallError, InterruptedException
    {
        call("startSmallDisplacements", new Object[0]).get();
    }

    public void stopSmallDisplacements()
            throws CallError, InterruptedException
    {
        call("stopSmallDisplacements", new Object[0]).get();
    }

    public void setExpressiveListeningEnabled(Boolean enable)
            throws CallError, InterruptedException
    {
        call("setExpressiveListeningEnabled", new Object[] { enable }).get();
    }

    public Boolean getExpressiveListeningEnabled()
            throws CallError, InterruptedException
    {
        return (Boolean)call("getExpressiveListeningEnabled", new Object[0]).get();
    }

    public void setBackgroundStrategy(String strategy)
            throws CallError, InterruptedException
    {
        call("setBackgroundStrategy", new Object[] { strategy }).get();
    }

    public String getBackgroundStrategy()
            throws CallError, InterruptedException
    {
        return (String)call("getBackgroundStrategy", new Object[0]).get();
    }

    public class AsyncALAutonomousMoves
            extends ALProxy
    {
        protected AsyncALAutonomousMoves() {}

        public Future<Boolean> isStatsEnabled()
                throws CallError, InterruptedException
        {
            return call("isStatsEnabled", new Object[0]);
        }

        public Future<Void> clearStats()
                throws CallError, InterruptedException
        {
            return call("clearStats", new Object[0]);
        }

        public Future<Boolean> isTraceEnabled()
                throws CallError, InterruptedException
        {
            return call("isTraceEnabled", new Object[0]);
        }

        public Future<Void> exit()
                throws CallError, InterruptedException
        {
            return call("exit", new Object[0]);
        }

        public Future<String> version()
                throws CallError, InterruptedException
        {
            return call("version", new Object[0]);
        }

        public Future<Boolean> ping()
                throws CallError, InterruptedException
        {
            return call("ping", new Object[0]);
        }

        public Future<List<String>> getMethodList()
                throws CallError, InterruptedException
        {
            return call("getMethodList", new Object[0]);
        }

        public Future<Object> getMethodHelp(String methodName)
                throws CallError, InterruptedException
        {
            return call("getMethodHelp", new Object[] { methodName });
        }

        public Future<Object> getModuleHelp()
                throws CallError, InterruptedException
        {
            return call("getModuleHelp", new Object[0]);
        }

        public Future<Boolean> wait(Integer id, Integer timeoutPeriod)
                throws CallError, InterruptedException
        {
            return call("wait", new Object[] { id, timeoutPeriod });
        }

        public Future<Boolean> isRunning(Integer id)
                throws CallError, InterruptedException
        {
            return call("isRunning", new Object[] { id });
        }

        public Future<Void> stop(Integer id)
                throws CallError, InterruptedException
        {
            return call("stop", new Object[] { id });
        }

        public Future<String> getBrokerName()
                throws CallError, InterruptedException
        {
            return call("getBrokerName", new Object[0]);
        }

        public Future<String> getUsage(String name)
                throws CallError, InterruptedException
        {
            return call("getUsage", new Object[] { name });
        }

        public Future<Void> startSmallDisplacements()
                throws CallError, InterruptedException
        {
            return call("startSmallDisplacements", new Object[0]);
        }

        public Future<Void> stopSmallDisplacements()
                throws CallError, InterruptedException
        {
            return call("stopSmallDisplacements", new Object[0]);
        }

        public Future<Void> setExpressiveListeningEnabled(Boolean enable)
                throws CallError, InterruptedException
        {
            return call("setExpressiveListeningEnabled", new Object[] { enable });
        }

        public Future<Boolean> getExpressiveListeningEnabled()
                throws CallError, InterruptedException
        {
            return call("getExpressiveListeningEnabled", new Object[0]);
        }

        public Future<Void> setBackgroundStrategy(String strategy)
                throws CallError, InterruptedException
        {
            return call("setBackgroundStrategy", new Object[] { strategy });
        }

        public Future<String> getBackgroundStrategy()
                throws CallError, InterruptedException
        {
            return call("getBackgroundStrategy", new Object[0]);
        }
    }
}
