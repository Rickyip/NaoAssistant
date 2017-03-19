package com.yipkaming.naoassistant.strategy;

import com.yipkaming.naoassistant.model.Nao;
import com.yipkaming.naoassistant.model.VerbalReminder;

/**
 * Created by Yip on 3/2/2017.
 */

public class StopASR implements ConfirmAction {
    public static final String ENDING_SPEECH = "Speech recognition service is ended.";

    @Override
    public void confirm() {
        Nao nao = Nao.getInstance();
        if(nao.isRunning()){
            nao.endRecognitionService();
            try {
                nao.say(ENDING_SPEECH);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void decline() {
        Nao nao = Nao.getInstance();
        if(nao.isRunning()){
            try {
                nao.say(VerbalReminder.OK_THEN);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
