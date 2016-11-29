package com.yipkaming.naoassistant.model;

import android.util.Log;

import com.aldebaran.qi.Application;
import com.aldebaran.qi.CallError;
import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.EventCallback;
import com.aldebaran.qi.helper.proxies.ALMemory;
import com.aldebaran.qi.helper.proxies.ALMotion;
import com.aldebaran.qi.helper.proxies.ALSpeechRecognition;
import com.aldebaran.qi.helper.proxies.ALTextToSpeech;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yip on 13/10/2016.
 */

public class Nao {

    private static final String TAG = Config.getSimpleName(Nao.class);

    public static final String PORT = "9559";
    public static final String CONNECTION_HEADER = "tcp://";


    private static Nao instance;

    private String url;

    private Application app;
    private ALTextToSpeech alTextToSpeech;
    private ALSpeechRecognition alSpeechRecognition;
    private ALMemory alMemory;
    private ALMotion alMotion;

    private boolean running = false;

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
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void stop(){
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

//        alTextToSpeech.say(content);
    }

    public void startVoiceRecognition() throws Exception {
        if( alSpeechRecognition == null){
            alSpeechRecognition = new ALSpeechRecognition(getSession());
        }
        if( alMemory == null){
            alMemory = new ALMemory(getSession());
        }

        List<String> vocab = new ArrayList<>();
        vocab.add("Nao");
        vocab.add("Yes");
        vocab.add("No");

        alSpeechRecognition.setLanguage("English");
        alSpeechRecognition.setVocabulary(vocab, true);
        alSpeechRecognition.subscribe("Testing");


        while(isRunning()){
            alMemory.subscribeToEvent("WordRecognized", new EventCallback<List<String>>() {
                @Override
                public void onEvent(List<String> paramT) throws InterruptedException, CallError {
                    String word = paramT.get(0);
                    System.out.println("Word "+word);
                    Log.e(TAG, "onWordRecognized: "+ word );
                    if( alTextToSpeech == null){
                        try {
                            alTextToSpeech = new ALTextToSpeech(getSession());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if(word.equals("nao")) {
                        alTextToSpeech.say("How can I help you?");
                        alSpeechRecognition.pause(true);
//            alMotion.wakeUp();
                        alSpeechRecognition.pause(false);
//            isAwake = true;
                    }
                    else if (word.equals("yes")) {
                        alTextToSpeech.say("Good");
//                    alMotion.moveToward(0.6f, 0f, 0f);
                    }
                    else if (word.equals("No")) {
                        alTextToSpeech.say("OK");
//                    alMotion.moveToward(0f, 0f, 0f);
                    }
                }
            });
            Thread.sleep(1000);
        }

    }


    public void waitInVoiceRecognition() throws InterruptedException {
        alSpeechRecognition.wait();
    }

    public void endVoiceRecognition() throws InterruptedException, CallError {
        alSpeechRecognition.unsubscribe("Testing");
    }

    public void onWordRecognized(Object words) throws InterruptedException, CallError {
        String word = (String) ((List<Object>)words).get(0);
        System.out.println("Word "+word);
        Log.e(TAG, "onWordRecognized: "+ word );
        if(word.equals("nao")) {
            alTextToSpeech.say("How can I help you?");
            alSpeechRecognition.pause(true);
//            alMotion.wakeUp();
            alSpeechRecognition.pause(false);
//            isAwake = true;
        }
        else if (word.equals("yes")) {
            alTextToSpeech.say("Good");
            alMotion.moveToward(0.6f, 0f, 0f);
        }
        else if (word.equals("No")) {
            alTextToSpeech.say("OK");
            alMotion.moveToward(0f, 0f, 0f);
        }
    }

    public void onEnd(Float touch) throws Exception {
        if (touch == 1.0) {
            say("Application is stopping");
//            motion.rest();
            app.stop();
            alSpeechRecognition.unsubscribe("demo");
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
        say(VerbalReminder.CONNECTION_GREETING);
    }
}
