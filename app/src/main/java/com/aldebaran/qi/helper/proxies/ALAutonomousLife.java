package com.aldebaran.qi.helper.proxies;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.Future;
import com.aldebaran.qi.Session;
import com.aldebaran.qi.Tuple2;
import com.aldebaran.qi.helper.ALProxy;
import java.util.List;
import java.util.Map;

public class ALAutonomousLife
        extends ALProxy
{
    private AsyncALAutonomousLife asyncProxy;

    public ALAutonomousLife(Session session)
            throws Exception
    {
        super(session);
        this.asyncProxy = new AsyncALAutonomousLife();
        this.asyncProxy.setService(getService());
    }

    public AsyncALAutonomousLife async()
    {
        return this.asyncProxy;
    }

    public void stopAll()
            throws CallError, InterruptedException
    {
        call("stopAll", new Object[0]).get();
    }

    public List<Tuple2<String, Integer>> getStateHistory()
            throws CallError, InterruptedException
    {
        return (List)call("getStateHistory", new Object[0]).get();
    }

    public String focusedActivity()
            throws CallError, InterruptedException
    {
        return (String)call("focusedActivity", new Object[0]).get();
    }

    public List<Tuple2<String, Integer>> getStateHistory(Integer depth)
            throws CallError, InterruptedException
    {
        return (List)call("getStateHistory", new Object[] { depth }).get();
    }

    public Integer getLifeTime()
            throws CallError, InterruptedException
    {
        return (Integer)call("getLifeTime", new Object[0]).get();
    }

    public void switchFocus(String activity_name, Integer flags)
            throws CallError, InterruptedException
    {
        call("switchFocus", new Object[] { activity_name, flags }).get();
    }

    public void stopMonitoringLaunchpadConditions()
            throws CallError, InterruptedException
    {
        call("stopMonitoringLaunchpadConditions", new Object[0]).get();
    }

    public Boolean isMonitoringLaunchpadConditions()
            throws CallError, InterruptedException
    {
        return (Boolean)call("isMonitoringLaunchpadConditions", new Object[0]).get();
    }

    public void setLaunchpadPluginEnabled(String plugin_name, Boolean enabled)
            throws CallError, InterruptedException
    {
        call("setLaunchpadPluginEnabled", new Object[] { plugin_name, enabled }).get();
    }

    public List<String> getEnabledLaunchpadPlugins()
            throws CallError, InterruptedException
    {
        return (List)call("getEnabledLaunchpadPlugins", new Object[0]).get();
    }

    public void stopFocus()
            throws CallError, InterruptedException
    {
        call("stopFocus", new Object[0]).get();
    }

    public List<String> getLaunchpadPluginsForGroup(String group)
            throws CallError, InterruptedException
    {
        return (List)call("getLaunchpadPluginsForGroup", new Object[] { group }).get();
    }

    public void setRobotOffsetFromFloor(Float offset)
            throws CallError, InterruptedException
    {
        call("setRobotOffsetFromFloor", new Object[] { offset }).get();
    }

    public Float getRobotOffsetFromFloor()
            throws CallError, InterruptedException
    {
        return (Float)call("getRobotOffsetFromFloor", new Object[0]).get();
    }

    public void setSafeguardEnabled(String name, Boolean enabled)
            throws CallError, InterruptedException
    {
        call("setSafeguardEnabled", new Object[] { name, enabled }).get();
    }

    public Boolean isSafeguardEnabled(String name)
            throws CallError, InterruptedException
    {
        return (Boolean)call("isSafeguardEnabled", new Object[] { name }).get();
    }

    public String getActivityNature(String activity_name)
            throws CallError, InterruptedException
    {
        return (String)call("getActivityNature", new Object[] { activity_name }).get();
    }

    public Map<String, Map<String, Integer>> getActivityStatistics()
            throws CallError, InterruptedException
    {
        return (Map)call("getActivityStatistics", new Object[0]).get();
    }

    public void switchFocus(String activity_name)
            throws CallError, InterruptedException
    {
        call("switchFocus", new Object[] { activity_name }).get();
    }

    public Map<String, Map<String, Integer>> getAutonomousActivityStatistics()
            throws CallError, InterruptedException
    {
        return (Map)call("getAutonomousActivityStatistics", new Object[0]).get();
    }

    public List<Tuple2<String, Integer>> getFocusHistory()
            throws CallError, InterruptedException
    {
        return (List)call("getFocusHistory", new Object[0]).get();
    }

    public List<Tuple2<String, Integer>> getFocusHistory(Integer depth)
            throws CallError, InterruptedException
    {
        return (List)call("getFocusHistory", new Object[] { depth }).get();
    }

    public void startMonitoringLaunchpadConditions()
            throws CallError, InterruptedException
    {
        call("startMonitoringLaunchpadConditions", new Object[0]).get();
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

    public void setState(String state)
            throws CallError, InterruptedException
    {
        call("setState", new Object[] { state }).get();
    }

    public String getState()
            throws CallError, InterruptedException
    {
        return (String)call("getState", new Object[0]).get();
    }

    public class AsyncALAutonomousLife
            extends ALProxy
    {
        protected AsyncALAutonomousLife() {}

        public Future<Void> stopAll()
                throws CallError, InterruptedException
        {
            return call("stopAll", new Object[0]);
        }

        public Future<List<Tuple2<String, Integer>>> getStateHistory()
                throws CallError, InterruptedException
        {
            return call("getStateHistory", new Object[0]);
        }

        public Future<String> focusedActivity()
                throws CallError, InterruptedException
        {
            return call("focusedActivity", new Object[0]);
        }

        public Future<List<Tuple2<String, Integer>>> getStateHistory(Integer depth)
                throws CallError, InterruptedException
        {
            return call("getStateHistory", new Object[] { depth });
        }

        public Future<Integer> getLifeTime()
                throws CallError, InterruptedException
        {
            return call("getLifeTime", new Object[0]);
        }

        public Future<Void> switchFocus(String activity_name, Integer flags)
                throws CallError, InterruptedException
        {
            return call("switchFocus", new Object[] { activity_name, flags });
        }

        public Future<Void> stopMonitoringLaunchpadConditions()
                throws CallError, InterruptedException
        {
            return call("stopMonitoringLaunchpadConditions", new Object[0]);
        }

        public Future<Boolean> isMonitoringLaunchpadConditions()
                throws CallError, InterruptedException
        {
            return call("isMonitoringLaunchpadConditions", new Object[0]);
        }

        public Future<Void> setLaunchpadPluginEnabled(String plugin_name, Boolean enabled)
                throws CallError, InterruptedException
        {
            return call("setLaunchpadPluginEnabled", new Object[] { plugin_name, enabled });
        }

        public Future<List<String>> getEnabledLaunchpadPlugins()
                throws CallError, InterruptedException
        {
            return call("getEnabledLaunchpadPlugins", new Object[0]);
        }

        public Future<Void> stopFocus()
                throws CallError, InterruptedException
        {
            return call("stopFocus", new Object[0]);
        }

        public Future<List<String>> getLaunchpadPluginsForGroup(String group)
                throws CallError, InterruptedException
        {
            return call("getLaunchpadPluginsForGroup", new Object[] { group });
        }

        public Future<Void> setRobotOffsetFromFloor(Float offset)
                throws CallError, InterruptedException
        {
            return call("setRobotOffsetFromFloor", new Object[] { offset });
        }

        public Future<Float> getRobotOffsetFromFloor()
                throws CallError, InterruptedException
        {
            return call("getRobotOffsetFromFloor", new Object[0]);
        }

        public Future<Void> setSafeguardEnabled(String name, Boolean enabled)
                throws CallError, InterruptedException
        {
            return call("setSafeguardEnabled", new Object[] { name, enabled });
        }

        public Future<Boolean> isSafeguardEnabled(String name)
                throws CallError, InterruptedException
        {
            return call("isSafeguardEnabled", new Object[] { name });
        }

        public Future<String> getActivityNature(String activity_name)
                throws CallError, InterruptedException
        {
            return call("getActivityNature", new Object[] { activity_name });
        }

        public Future<Map<String, Map<String, Integer>>> getActivityStatistics()
                throws CallError, InterruptedException
        {
            return call("getActivityStatistics", new Object[0]);
        }

        public Future<Void> switchFocus(String activity_name)
                throws CallError, InterruptedException
        {
            return call("switchFocus", new Object[] { activity_name });
        }

        public Future<Map<String, Map<String, Integer>>> getAutonomousActivityStatistics()
                throws CallError, InterruptedException
        {
            return call("getAutonomousActivityStatistics", new Object[0]);
        }

        public Future<List<Tuple2<String, Integer>>> getFocusHistory()
                throws CallError, InterruptedException
        {
            return call("getFocusHistory", new Object[0]);
        }

        public Future<List<Tuple2<String, Integer>>> getFocusHistory(Integer depth)
                throws CallError, InterruptedException
        {
            return call("getFocusHistory", new Object[] { depth });
        }

        public Future<Void> startMonitoringLaunchpadConditions()
                throws CallError, InterruptedException
        {
            return call("startMonitoringLaunchpadConditions", new Object[0]);
        }

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

        public Future<Void> setState(String state)
                throws CallError, InterruptedException
        {
            return call("setState", new Object[] { state });
        }

        public Future<String> getState()
                throws CallError, InterruptedException
        {
            return call("getState", new Object[0]);
        }
    }
}
