package com.yipkaming.naoassistant.model;

import com.aldebaran.qi.Application;
import com.aldebaran.qi.Session;

/**
 * Created by Yip on 13/10/2016.
 */

public class Nao {

    private static Nao instance;

    private String _IP;
    private Application app;

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
            _IP = IP;

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

    public String get_IP(){
        return _IP;
    }

    public synchronized static Nao getInstance() {
        if(instance == null){
            instance = new Nao();
        }
        return instance;
    }
}
