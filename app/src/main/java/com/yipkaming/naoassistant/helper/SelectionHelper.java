package com.yipkaming.naoassistant.helper;

import com.yipkaming.naoassistant.model.NotificationMessage;

/**
 * Created by Yip on 7/11/2016.
 */

public class SelectionHelper {

    public static void process(NotificationMessage notificationMessage){
        // 1. important application?
        // 2. if no, any keywords in content/ title?
        // 3. if 1 yes or 2 yes, pass to verbal message class
        // 4. if both no, notificationMessage.setImportance(1), and push to nao
        // 5. notificationMessage.save()   save to db
        // 6. end
    }
}
