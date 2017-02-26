package com.yipkaming.naoassistant.model;

import android.util.Log;

import com.aldebaran.qi.Application;
import com.aldebaran.qi.CallError;
import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.proxies.ALMemory;
import com.aldebaran.qi.helper.proxies.ALMotion;
import com.aldebaran.qi.helper.proxies.ALSpeechRecognition;
import com.aldebaran.qi.helper.proxies.ALTextToSpeech;
import com.yipkaming.naoassistant.helper.SelectionHelper;
import com.yipkaming.naoassistant.strategy.ConfirmAction;
import com.yipkaming.naoassistant.strategy.ReadNotification;
import com.yipkaming.naoassistant.strategy.StopASR;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

/**
 * Created by Yip on 13/10/2016.
 */

public class Nao {

    private static final String TAG = Config.getSimpleName(Nao.class);

    public static final String PORT = "9559";
    public static final String CONNECTION_HEADER = "tcp://";
    private static final String ASR_SUBSCRIBER = "interaction";

    private static final String ENGLISH = "English";
    private static final String WORD_RECOGNIZED = "WordRecognized";

    private static Nao instance;

    private Application app;
    private ALTextToSpeech alTextToSpeech;
    private ALSpeechRecognition alSpeechRecognition;
    private ALMemory alMemory;
    private ALMotion alMotion;

    private ConfirmAction confirmAction;

    private String url;
    private boolean running = false;
    private boolean isInit = true;

    public boolean isRunning(){
        return running;
    }

    private Nao(){};

    public void init(String IP){
        try {
            String[] args = new String[2];
            args[0] = "--qi-url";
            args[1] = IP;
            url = IP;

            app = new Application(args);
            app.start();
            running = true;
            isInit = true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void stop(){
        if(alSpeechRecognition != null) {
            endRecognitionService();
        }
        running = false;
        app.stop();
    }

    public Session getSession() throws Exception {
        if(!running)
            throw new Exception("disconnected");
        return app.session();
    }

    public String getUrl(){
        return url;
    }

    public synchronized static Nao getInstance() {
        if(instance == null){
            instance = new Nao();
        }
        return instance;
    }

    public void say(String content) throws Exception {
        if( alTextToSpeech == null){
            alTextToSpeech = new ALTextToSpeech(getSession());
        }

        alTextToSpeech.setParameter("defaultVoiceSpeed", (float) 800.0);
        alTextToSpeech.setParameter("pitchShift", (float) 1.15
        );

        if( alSpeechRecognition != null){
            alSpeechRecognition.pause(true);
        }

        alTextToSpeech.say(content);

        if( alSpeechRecognition != null){
            alSpeechRecognition.pause(false);
        }
    }

    public void startVoiceRecognition() throws Exception {
        isInit = false;
        if( alSpeechRecognition == null){
            alSpeechRecognition = new ALSpeechRecognition(getSession());
        }
        if( alMemory == null){
            alMemory = new ALMemory(getSession());
        }

        List<String> vocab = new ArrayList<>();
        vocab.add("Nao");
        vocab.add("Yes please");
        vocab.add("Stop ASR");
        vocab.add("Read notifications");
        vocab.add("How are you?");
        vocab.add("OK");
        vocab.add("Stop speech recognition service");
        vocab.add("Any missed call");

        alSpeechRecognition.setLanguage(ENGLISH);
        alSpeechRecognition.setVocabulary(vocab, false);
        alSpeechRecognition.setParameter("Sensitivity", (float) 0.5);  // not effective
        /*
            Sensitivity: Value between 0 and 1 setting the sensitivity of the voice activity detector used by the engine.
            NbHypotheses: Number of hypotheses returned by the engine. Default: 1
            http://doc.aldebaran.com/2-1/naoqi/audio/alspeechrecognition-api.html#ALSpeechRecognitionProxy::setParameter__ssCR.floatCR
         */
        alSpeechRecognition.subscribe(ASR_SUBSCRIBER);

        alMemory.subscribeToEvent(WORD_RECOGNIZED, "onWordRecognized::(m)", this);
        alMemory.subscribeToEvent("MiddleTactilTouched", "onEnd::(f)", this);
        app.run();
    }


    public void onWordRecognized(Object words) throws Exception {
        String word = (String) ((List<Object>)words).get(0);
        Log.e(TAG, "onWordRecognized: "+ word );

        if(alTextToSpeech == null){
            alTextToSpeech = new ALTextToSpeech(getSession());
        }

        alTextToSpeech.setParameter("speed", (float) 8000);

        alSpeechRecognition.pause(true);
        switch (word) {
            case "Nao":
                alTextToSpeech.say("How can I help you?");
                break;
            case "Yes please":
                if(confirmAction != null){
                    confirmAction.confirm();
                    setConfirmAction(null);
                }else {
                    alTextToSpeech.say("Good");
                }
//                SelectionHelper.read(this);
                break;
            case "OK":
                if(confirmAction != null){
                    confirmAction.confirm();
                    setConfirmAction(null);
                }else {
                    alTextToSpeech.say("Good");
                }
//                SelectionHelper.read(this);
                break;
            case "How are you?":
                alTextToSpeech.say("I am fine, thank you");
                break;
            case "Read notifications":
                SelectionHelper.read(this);
                break;
            case "Stop ASR":
                alTextToSpeech.say("Do you mean to stop speech recognition service?");
                confirmAction = new StopASR();
//                endRecognitionService();
                break;
            case "Stop speech recognition service":
                endRecognitionService();
                break;
            case "Any missed call":
                SelectionHelper.findMissedCalls(this);
                break;
            case "":
                break;
            default:
                alTextToSpeech.say("Sorry I don't understand");
                break;
        }
        alSpeechRecognition.pause(false);
    }

    public void onEnd(Float touch) throws Exception {
        if (touch == 1.0) {
            app.stop();
            endRecognitionService();
        }

    }

    public void endRecognitionService(){
        try {
            alSpeechRecognition.unsubscribe(ASR_SUBSCRIBER);
            alTextToSpeech.say("I am stopping speech recognition service. ");
        } catch (CallError | InterruptedException callError) {
            callError.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getIpAddress(){
        if(isRunning()){
            String ip = getUrl().replace(":"+PORT, "");
            return ip.substring(6);
        }
        return "";
    }

    public void sayConnectionGreeting() throws Exception {
        say(VerbalReminder.CONNECTION_GREETING + VerbalReminder.INTRODUCTION);
        List<NotificationMessage> aboutToRead = NotificationMessage.findReadable(Realm.getDefaultInstance(), SelectionHelper.getImportanceThreshold());
        if(aboutToRead.isEmpty()){
            say(VerbalReminder.NO_NOTIFICATION);
        }else {
            say(VerbalReminder.getNotificationGreeting(aboutToRead.size()));
            confirmAction = new ReadNotification();
        }
    }

    public ConfirmAction getConfirmAction() {
        return confirmAction;
    }

    public void setConfirmAction(ConfirmAction confirmAction) {
        this.confirmAction = confirmAction;
    }

    public boolean isInit() {
        return isInit;
    }

    public void setInit(boolean init) {
        isInit = init;
    }
}
