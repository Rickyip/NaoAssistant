package com.yipkaming.naoassistant.strategy;

import com.yipkaming.naoassistant.model.Keyword;
import com.yipkaming.naoassistant.model.Nao;
import com.yipkaming.naoassistant.model.VerbalReminder;

/**
 * Created by Yip on 18/3/2017.
 */

public class ChangePreference implements ConfirmAction {

    private String type;

    public ChangePreference(String type) {
        this.type = type;
    }


    @Override
    public void confirm() {
        Keyword.changeKeyWordsPreference(type, true);
        greeting();
    }

    @Override
    public void decline() {
        Keyword.changeKeyWordsPreference(type, false);
        greeting();
    }

    private void greeting(){
        Nao nao = Nao.getInstance();
        try {
            nao.say(VerbalReminder.PREFERNCE_SETTING_FINISHED);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
