package com.yipkaming.naoassistant.strategy;

import com.yipkaming.naoassistant.model.Nao;
import com.yipkaming.naoassistant.model.VerbalReminder;

/**
 * Created by Yip on 18/3/2017.
 */

public class ReadTutorial implements ConfirmAction {
    @Override
    public void confirm() {
        Nao nao = Nao.getInstance();
        if(nao.isRunning()){
            try {
                nao.say(VerbalReminder.ALLTUTORIAL);
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
