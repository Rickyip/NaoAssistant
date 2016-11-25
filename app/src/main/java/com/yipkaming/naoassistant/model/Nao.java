package com.yipkaming.naoassistant.model;

import com.aldebaran.qi.Application;
import com.aldebaran.qi.CallError;
import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.proxies.ALMemory;
import com.aldebaran.qi.helper.proxies.ALSpeechRecognition;
import com.aldebaran.qi.helper.proxies.ALTextToSpeech;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yip on 13/10/2016.
 */

public class Nao {

    public static final String PORT = "9559";
    public static final String CONNECTION_HEADER = "tcp://";


    private static Nao instance;

    private String url;
    private Application app;
    private ALTextToSpeech alTextToSpeech;
    private ALSpeechRecognition alSpeechRecognition;
    private ALMemory alMemory;
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
        List<String> vocab = new ArrayList<>();
        vocab.add("Nao");
        vocab.add("Yes");
        vocab.add("No");

        alSpeechRecognition.setLanguage("English");
        alSpeechRecognition.setVocabulary(vocab, true);
        alSpeechRecognition.subscribe("Testing");

    }


    public void waitInVoiceRecognition() throws InterruptedException {
        alSpeechRecognition.wait();
    }

    public void endVoiceRecognition() throws InterruptedException, CallError {
        alSpeechRecognition.unsubscribe("Testing");
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
