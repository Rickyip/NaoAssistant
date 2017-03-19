package com.yipkaming.naoassistant.strategy;

import com.yipkaming.naoassistant.helper.SelectionHelper;
import com.yipkaming.naoassistant.model.Nao;
import com.yipkaming.naoassistant.model.VerbalReminder;

/**
 * Created by Yip on 3/2/2017.
 */

public class ReadNotification implements ConfirmAction {
    @Override
    public void confirm() {
        Nao nao = Nao.getInstance();
        if(nao.isRunning()){
            SelectionHelper.read(nao);
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
