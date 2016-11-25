package com.aldebaran.qi.helper.proxies;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.Future;
import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.ALProxy;
import java.util.List;

public class ALMotion
        extends ALProxy
{
    public ALMotion(Session session)
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

    public void wakeUp()
            throws CallError, InterruptedException
    {
        call("wakeUp", new Object[0]).get();
    }

    public void rest()
            throws CallError, InterruptedException
    {
        call("rest", new Object[0]).get();
    }

    public void rest(String param1)
            throws CallError, InterruptedException
    {
        call("rest", new Object[] { param1 }).get();
    }

    public Boolean robotIsWakeUp()
            throws CallError, InterruptedException
    {
        return (Boolean)call("robotIsWakeUp", new Object[0]).get();
    }

    public void stiffnessInterpolation(Object names, Object stiffnessLists, Object timeLists)
            throws CallError, InterruptedException
    {
        call("stiffnessInterpolation", new Object[] { names, stiffnessLists, timeLists }).get();
    }

    public void setStiffnesses(Object names, Object stiffnesses)
            throws CallError, InterruptedException
    {
        call("setStiffnesses", new Object[] { names, stiffnesses }).get();
    }

    public List<Float> getStiffnesses(Object jointName)
            throws CallError, InterruptedException
    {
        return (List)call("getStiffnesses", new Object[] { jointName }).get();
    }

    public void angleInterpolation(Object names, Object angleLists, Object timeLists, Boolean isAbsolute)
            throws CallError, InterruptedException
    {
        call("angleInterpolation", new Object[] { names, angleLists, timeLists, isAbsolute }).get();
    }

    public void angleInterpolationWithSpeed(Object names, Object targetAngles, Float maxSpeedFraction)
            throws CallError, InterruptedException
    {
        call("angleInterpolationWithSpeed", new Object[] { names, targetAngles, maxSpeedFraction }).get();
    }

    public void angleInterpolationBezier(List<String> jointNames, Object times, Object controlPoints)
            throws CallError, InterruptedException
    {
        call("angleInterpolationBezier", new Object[] { jointNames, times, controlPoints }).get();
    }

    public void setAngles(Object names, Object angles, Float fractionMaxSpeed)
            throws CallError, InterruptedException
    {
        call("setAngles", new Object[] { names, angles, fractionMaxSpeed }).get();
    }

    public void changeAngles(Object names, Object changes, Float fractionMaxSpeed)
            throws CallError, InterruptedException
    {
        call("changeAngles", new Object[] { names, changes, fractionMaxSpeed }).get();
    }

    public List<Float> getAngles(Object names, Boolean useSensors)
            throws CallError, InterruptedException
    {
        return (List)call("getAngles", new Object[] { names, useSensors }).get();
    }

    public void openHand(String handName)
            throws CallError, InterruptedException
    {
        call("openHand", new Object[] { handName }).get();
    }

    public void closeHand(String handName)
            throws CallError, InterruptedException
    {
        call("closeHand", new Object[] { handName }).get();
    }

    public void move(Float x, Float y, Float theta)
            throws CallError, InterruptedException
    {
        call("move", new Object[] { x, y, theta }).get();
    }

    public void move(Float x, Float y, Float theta, Object moveConfig)
            throws CallError, InterruptedException
    {
        call("move", new Object[] { x, y, theta, moveConfig }).get();
    }

    public void moveToward(Float x, Float y, Float theta)
            throws CallError, InterruptedException
    {
        call("moveToward", new Object[] { x, y, theta }).get();
    }

    public void moveToward(Float x, Float y, Float theta, Object moveConfig)
            throws CallError, InterruptedException
    {
        call("moveToward", new Object[] { x, y, theta, moveConfig }).get();
    }

    public void setWalkTargetVelocity(Float x, Float y, Float theta, Float frequency)
            throws CallError, InterruptedException
    {
        call("setWalkTargetVelocity", new Object[] { x, y, theta, frequency }).get();
    }

    public void setWalkTargetVelocity(Float x, Float y, Float theta, Float frequency, Object feetGaitConfig)
            throws CallError, InterruptedException
    {
        call("setWalkTargetVelocity", new Object[] { x, y, theta, frequency, feetGaitConfig }).get();
    }

    public void setWalkTargetVelocity(Float x, Float y, Float theta, Float frequency, Object leftFootMoveConfig, Object rightFootMoveConfig)
            throws CallError, InterruptedException
    {
        call("setWalkTargetVelocity", new Object[] { x, y, theta, frequency, leftFootMoveConfig, rightFootMoveConfig }).get();
    }

    public void moveTo(Float x, Float y, Float theta)
            throws CallError, InterruptedException
    {
        call("moveTo", new Object[] { x, y, theta }).get();
    }

    public void moveTo(Float x, Float y, Float theta, Float time)
            throws CallError, InterruptedException
    {
        call("moveTo", new Object[] { x, y, theta, time }).get();
    }

    public void moveTo(Float x, Float y, Float theta, Object moveConfig)
            throws CallError, InterruptedException
    {
        call("moveTo", new Object[] { x, y, theta, moveConfig }).get();
    }

    public void moveTo(Float x, Float y, Float theta, Float time, Object moveConfig)
            throws CallError, InterruptedException
    {
        call("moveTo", new Object[] { x, y, theta, time, moveConfig }).get();
    }

    public void moveTo(Object controlPoint)
            throws CallError, InterruptedException
    {
        call("moveTo", new Object[] { controlPoint }).get();
    }

    public void moveTo(Object controlPoint, Object moveConfig)
            throws CallError, InterruptedException
    {
        call("moveTo", new Object[] { controlPoint, moveConfig }).get();
    }

    public void walkTo(Float x, Float y, Float theta)
            throws CallError, InterruptedException
    {
        call("walkTo", new Object[] { x, y, theta }).get();
    }

    public void walkTo(Float x, Float y, Float theta, Object feetGaitConfig)
            throws CallError, InterruptedException
    {
        call("walkTo", new Object[] { x, y, theta, feetGaitConfig }).get();
    }

    public void walkTo(Object controlPoint)
            throws CallError, InterruptedException
    {
        call("walkTo", new Object[] { controlPoint }).get();
    }

    public void walkTo(Object controlPoint, Object feetGaitConfig)
            throws CallError, InterruptedException
    {
        call("walkTo", new Object[] { controlPoint, feetGaitConfig }).get();
    }

    public void setFootSteps(List<String> legName, Object footSteps, List<Float> timeList, Boolean clearExisting)
            throws CallError, InterruptedException
    {
        call("setFootSteps", new Object[] { legName, footSteps, timeList, clearExisting }).get();
    }

    public void setFootStepsWithSpeed(List<String> legName, Object footSteps, List<Float> fractionMaxSpeed, Boolean clearExisting)
            throws CallError, InterruptedException
    {
        call("setFootStepsWithSpeed", new Object[] { legName, footSteps, fractionMaxSpeed, clearExisting }).get();
    }

    public Object getFootSteps()
            throws CallError, InterruptedException
    {
        return call("getFootSteps", new Object[0]).get();
    }

    public void walkInit()
            throws CallError, InterruptedException
    {
        call("walkInit", new Object[0]).get();
    }

    public void moveInit()
            throws CallError, InterruptedException
    {
        call("moveInit", new Object[0]).get();
    }

    public void waitUntilWalkIsFinished()
            throws CallError, InterruptedException
    {
        call("waitUntilWalkIsFinished", new Object[0]).get();
    }

    public void waitUntilMoveIsFinished()
            throws CallError, InterruptedException
    {
        call("waitUntilMoveIsFinished", new Object[0]).get();
    }

    public Boolean walkIsActive()
            throws CallError, InterruptedException
    {
        return (Boolean)call("walkIsActive", new Object[0]).get();
    }

    public Boolean moveIsActive()
            throws CallError, InterruptedException
    {
        return (Boolean)call("moveIsActive", new Object[0]).get();
    }

    public void stopWalk()
            throws CallError, InterruptedException
    {
        call("stopWalk", new Object[0]).get();
    }

    public void stopMove()
            throws CallError, InterruptedException
    {
        call("stopMove", new Object[0]).get();
    }

    public Object getFootGaitConfig(String config)
            throws CallError, InterruptedException
    {
        return call("getFootGaitConfig", new Object[] { config }).get();
    }

    public Object getMoveConfig(String config)
            throws CallError, InterruptedException
    {
        return call("getMoveConfig", new Object[] { config }).get();
    }

    public List<Float> getRobotPosition(Boolean useSensors)
            throws CallError, InterruptedException
    {
        return (List)call("getRobotPosition", new Object[] { useSensors }).get();
    }

    public List<Float> getNextRobotPosition()
            throws CallError, InterruptedException
    {
        return (List)call("getNextRobotPosition", new Object[0]).get();
    }

    public List<Float> getRobotVelocity()
            throws CallError, InterruptedException
    {
        return (List)call("getRobotVelocity", new Object[0]).get();
    }

    public Object getWalkArmsEnabled()
            throws CallError, InterruptedException
    {
        return call("getWalkArmsEnabled", new Object[0]).get();
    }

    public void setWalkArmsEnabled(Boolean leftArmEnabled, Boolean rightArmEnabled)
            throws CallError, InterruptedException
    {
        call("setWalkArmsEnabled", new Object[] { leftArmEnabled, rightArmEnabled }).get();
    }

    public Boolean getMoveArmsEnabled(String chainName)
            throws CallError, InterruptedException
    {
        return (Boolean)call("getMoveArmsEnabled", new Object[] { chainName }).get();
    }

    public void setMoveArmsEnabled(Boolean leftArmEnabled, Boolean rightArmEnabled)
            throws CallError, InterruptedException
    {
        call("setMoveArmsEnabled", new Object[] { leftArmEnabled, rightArmEnabled }).get();
    }

    public void positionInterpolation(String chainName, Integer space, Object path, Integer axisMask, Object durations, Boolean isAbsolute)
            throws CallError, InterruptedException
    {
        call("positionInterpolation", new Object[] { chainName, space, path, axisMask, durations, isAbsolute }).get();
    }

    public void positionInterpolations(List<String> effectorNames, Integer taskSpaceForAllPaths, Object paths, Object axisMasks, Object relativeTimes, Boolean isAbsolute)
            throws CallError, InterruptedException
    {
        call("positionInterpolations", new Object[] { effectorNames, taskSpaceForAllPaths, paths, axisMasks, relativeTimes, isAbsolute }).get();
    }

    public void positionInterpolations(Object effectorNames, Object taskSpaceForAllPaths, Object paths, Object axisMasks, Object relativeTimes)
            throws CallError, InterruptedException
    {
        call("positionInterpolations", new Object[] { effectorNames, taskSpaceForAllPaths, paths, axisMasks, relativeTimes }).get();
    }

    public void setPosition(String chainName, Integer space, List<Float> position, Float fractionMaxSpeed, Integer axisMask)
            throws CallError, InterruptedException
    {
        call("setPosition", new Object[] { chainName, space, position, fractionMaxSpeed, axisMask }).get();
    }

    public void setPositions(Object names, Object spaces, Object positions, Float fractionMaxSpeed, Object axisMask)
            throws CallError, InterruptedException
    {
        call("setPositions", new Object[] { names, spaces, positions, fractionMaxSpeed, axisMask }).get();
    }

    public void changePosition(String effectorName, Integer space, List<Float> positionChange, Float fractionMaxSpeed, Integer axisMask)
            throws CallError, InterruptedException
    {
        call("changePosition", new Object[] { effectorName, space, positionChange, fractionMaxSpeed, axisMask }).get();
    }

    public List<Float> getPosition(String name, Integer space, Boolean useSensorValues)
            throws CallError, InterruptedException
    {
        return (List)call("getPosition", new Object[] { name, space, useSensorValues }).get();
    }

    public void transformInterpolation(String chainName, Integer space, Object path, Integer axisMask, Object duration, Boolean isAbsolute)
            throws CallError, InterruptedException
    {
        call("transformInterpolation", new Object[] { chainName, space, path, axisMask, duration, isAbsolute }).get();
    }

    public void transformInterpolations(List<String> effectorNames, Integer taskSpaceForAllPaths, Object paths, Object axisMasks, Object relativeTimes, Boolean isAbsolute)
            throws CallError, InterruptedException
    {
        call("transformInterpolations", new Object[] { effectorNames, taskSpaceForAllPaths, paths, axisMasks, relativeTimes, isAbsolute }).get();
    }

    public void transformInterpolations(Object effectorNames, Object taskSpaceForAllPaths, Object paths, Object axisMasks, Object relativeTimes)
            throws CallError, InterruptedException
    {
        call("transformInterpolations", new Object[] { effectorNames, taskSpaceForAllPaths, paths, axisMasks, relativeTimes }).get();
    }

    public void setTransform(String chainName, Integer space, List<Float> transform, Float fractionMaxSpeed, Integer axisMask)
            throws CallError, InterruptedException
    {
        call("setTransform", new Object[] { chainName, space, transform, fractionMaxSpeed, axisMask }).get();
    }

    public void setTransforms(Object names, Object spaces, Object transforms, Float fractionMaxSpeed, Object axisMask)
            throws CallError, InterruptedException
    {
        call("setTransforms", new Object[] { names, spaces, transforms, fractionMaxSpeed, axisMask }).get();
    }

    public void changeTransform(String chainName, Integer space, List<Float> transform, Float fractionMaxSpeed, Integer axisMask)
            throws CallError, InterruptedException
    {
        call("changeTransform", new Object[] { chainName, space, transform, fractionMaxSpeed, axisMask }).get();
    }

    public List<Float> getTransform(String name, Integer space, Boolean useSensorValues)
            throws CallError, InterruptedException
    {
        return (List)call("getTransform", new Object[] { name, space, useSensorValues }).get();
    }

    public void wbEnable(Boolean isEnabled)
            throws CallError, InterruptedException
    {
        call("wbEnable", new Object[] { isEnabled }).get();
    }

    public void wbFootState(String stateName, String supportLeg)
            throws CallError, InterruptedException
    {
        call("wbFootState", new Object[] { stateName, supportLeg }).get();
    }

    public void wbEnableBalanceConstraint(Boolean isEnable, String supportLeg)
            throws CallError, InterruptedException
    {
        call("wbEnableBalanceConstraint", new Object[] { isEnable, supportLeg }).get();
    }

    public void wbGoToBalance(String supportLeg, Float duration)
            throws CallError, InterruptedException
    {
        call("wbGoToBalance", new Object[] { supportLeg, duration }).get();
    }

    public void wbEnableEffectorControl(String effectorName, Boolean isEnabled)
            throws CallError, InterruptedException
    {
        call("wbEnableEffectorControl", new Object[] { effectorName, isEnabled }).get();
    }

    public void wbSetEffectorControl(String effectorName, Object targetCoordinate)
            throws CallError, InterruptedException
    {
        call("wbSetEffectorControl", new Object[] { effectorName, targetCoordinate }).get();
    }

    public void wbEnableEffectorOptimization(String effectorName, Boolean isActive)
            throws CallError, InterruptedException
    {
        call("wbEnableEffectorOptimization", new Object[] { effectorName, isActive }).get();
    }

    public Boolean setCollisionProtectionEnabled(String pChainName, Boolean pEnable)
            throws CallError, InterruptedException
    {
        return (Boolean)call("setCollisionProtectionEnabled", new Object[] { pChainName, pEnable }).get();
    }

    public Boolean getCollisionProtectionEnabled(String pChainName)
            throws CallError, InterruptedException
    {
        return (Boolean)call("getCollisionProtectionEnabled", new Object[] { pChainName }).get();
    }

    public void setExternalCollisionProtectionEnabled(String pName, Boolean pEnable)
            throws CallError, InterruptedException
    {
        call("setExternalCollisionProtectionEnabled", new Object[] { pName, pEnable }).get();
    }

    public List<Float> getChainClosestObstaclePosition(String pName, Integer space)
            throws CallError, InterruptedException
    {
        return (List)call("getChainClosestObstaclePosition", new Object[] { pName, space }).get();
    }

    public Boolean getExternalCollisionProtectionEnabled(String pName)
            throws CallError, InterruptedException
    {
        return (Boolean)call("getExternalCollisionProtectionEnabled", new Object[] { pName }).get();
    }

    public void setOrthogonalSecurityDistance(Float securityDistance)
            throws CallError, InterruptedException
    {
        call("setOrthogonalSecurityDistance", new Object[] { securityDistance }).get();
    }

    public Float getOrthogonalSecurityDistance()
            throws CallError, InterruptedException
    {
        return (Float)call("getOrthogonalSecurityDistance", new Object[0]).get();
    }

    public void setTangentialSecurityDistance(Float securityDistance)
            throws CallError, InterruptedException
    {
        call("setTangentialSecurityDistance", new Object[] { securityDistance }).get();
    }

    public Float getTangentialSecurityDistance()
            throws CallError, InterruptedException
    {
        return (Float)call("getTangentialSecurityDistance", new Object[0]).get();
    }

    public String isCollision(String pChainName)
            throws CallError, InterruptedException
    {
        return (String)call("isCollision", new Object[] { pChainName }).get();
    }

    public void setFallManagerEnabled(Boolean pEnable)
            throws CallError, InterruptedException
    {
        call("setFallManagerEnabled", new Object[] { pEnable }).get();
    }

    public Boolean getFallManagerEnabled()
            throws CallError, InterruptedException
    {
        return (Boolean)call("getFallManagerEnabled", new Object[0]).get();
    }

    public void setPushRecoveryEnabled(Boolean pEnable)
            throws CallError, InterruptedException
    {
        call("setPushRecoveryEnabled", new Object[] { pEnable }).get();
    }

    public Boolean getPushRecoveryEnabled()
            throws CallError, InterruptedException
    {
        return (Boolean)call("getPushRecoveryEnabled", new Object[0]).get();
    }

    public void setSmartStiffnessEnabled(Boolean pEnable)
            throws CallError, InterruptedException
    {
        call("setSmartStiffnessEnabled", new Object[] { pEnable }).get();
    }

    public Boolean getSmartStiffnessEnabled()
            throws CallError, InterruptedException
    {
        return (Boolean)call("getSmartStiffnessEnabled", new Object[0]).get();
    }

    public void setDiagnosisEffectEnabled(Boolean pEnable)
            throws CallError, InterruptedException
    {
        call("setDiagnosisEffectEnabled", new Object[] { pEnable }).get();
    }

    public Boolean getDiagnosisEffectEnabled()
            throws CallError, InterruptedException
    {
        return (Boolean)call("getDiagnosisEffectEnabled", new Object[0]).get();
    }

    public List<String> getJointNames(String name)
            throws CallError, InterruptedException
    {
        return (List)call("getJointNames", new Object[] { name }).get();
    }

    public List<String> getBodyNames(String name)
            throws CallError, InterruptedException
    {
        return (List)call("getBodyNames", new Object[] { name }).get();
    }

    public List<String> getSensorNames()
            throws CallError, InterruptedException
    {
        return (List)call("getSensorNames", new Object[0]).get();
    }

    public Object getLimits(String name)
            throws CallError, InterruptedException
    {
        return call("getLimits", new Object[] { name }).get();
    }

    public Integer getMotionCycleTime()
            throws CallError, InterruptedException
    {
        return (Integer)call("getMotionCycleTime", new Object[0]).get();
    }

    public Object getRobotConfig()
            throws CallError, InterruptedException
    {
        return call("getRobotConfig", new Object[0]).get();
    }

    public String getSummary()
            throws CallError, InterruptedException
    {
        return (String)call("getSummary", new Object[0]).get();
    }

    public Float getMass(String pName)
            throws CallError, InterruptedException
    {
        return (Float)call("getMass", new Object[] { pName }).get();
    }

    public List<Float> getCOM(String pName, Integer pSpace, Boolean pUseSensorValues)
            throws CallError, InterruptedException
    {
        return (List)call("getCOM", new Object[] { pName, pSpace, pUseSensorValues }).get();
    }

    public void setMotionConfig(Object config)
            throws CallError, InterruptedException
    {
        call("setMotionConfig", new Object[] { config }).get();
    }

    public void updateTrackerTarget(Float pTargetPositionWy, Float pTargetPositionWz, Integer pTimeSinceDetectionMs, Boolean pUseOfWholeBody)
            throws CallError, InterruptedException
    {
        call("updateTrackerTarget", new Object[] { pTargetPositionWy, pTargetPositionWz, pTimeSinceDetectionMs, pUseOfWholeBody }).get();
    }

    public void setBreathEnabled(String pChain, Boolean pIsEnabled)
            throws CallError, InterruptedException
    {
        call("setBreathEnabled", new Object[] { pChain, pIsEnabled }).get();
    }

    public Boolean getBreathEnabled(String pChain)
            throws CallError, InterruptedException
    {
        return (Boolean)call("getBreathEnabled", new Object[] { pChain }).get();
    }

    public void setBreathConfig(Object pConfig)
            throws CallError, InterruptedException
    {
        call("setBreathConfig", new Object[] { pConfig }).get();
    }

    public Object getBreathConfig()
            throws CallError, InterruptedException
    {
        return call("getBreathConfig", new Object[0]).get();
    }

    public void setIdlePostureEnabled(String pChain, Boolean pIsEnabled)
            throws CallError, InterruptedException
    {
        call("setIdlePostureEnabled", new Object[] { pChain, pIsEnabled }).get();
    }

    public Boolean getIdlePostureEnabled(String pChain)
            throws CallError, InterruptedException
    {
        return (Boolean)call("getIdlePostureEnabled", new Object[] { pChain }).get();
    }

    public Object getTaskList()
            throws CallError, InterruptedException
    {
        return call("getTaskList", new Object[0]).get();
    }

    public Boolean areResourcesAvailable(List<String> resourceNames)
            throws CallError, InterruptedException
    {
        return (Boolean)call("areResourcesAvailable", new Object[] { resourceNames }).get();
    }

    public Boolean killTask(Integer motionTaskID)
            throws CallError, InterruptedException
    {
        return (Boolean)call("killTask", new Object[] { motionTaskID }).get();
    }

    public void killTasksUsingResources(List<String> resourceNames)
            throws CallError, InterruptedException
    {
        call("killTasksUsingResources", new Object[] { resourceNames }).get();
    }

    public void killWalk()
            throws CallError, InterruptedException
    {
        call("killWalk", new Object[0]).get();
    }

    public void killMove()
            throws CallError, InterruptedException
    {
        call("killMove", new Object[0]).get();
    }

    public void killAll()
            throws CallError, InterruptedException
    {
        call("killAll", new Object[0]).get();
    }

    public void setEnableNotifications(Boolean enable)
            throws CallError, InterruptedException
    {
        call("setEnableNotifications", new Object[] { enable }).get();
    }

    public Boolean areNotificationsEnabled()
            throws CallError, InterruptedException
    {
        return (Boolean)call("areNotificationsEnabled", new Object[0]).get();
    }
}
