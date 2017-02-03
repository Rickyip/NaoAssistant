package com.yipkaming.naoassistant.strategy;

import com.yipkaming.naoassistant.helper.SelectionHelper;
import com.yipkaming.naoassistant.model.Nao;

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
}
