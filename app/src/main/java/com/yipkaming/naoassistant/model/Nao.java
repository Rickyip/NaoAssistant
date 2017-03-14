package com.yipkaming.naoassistant.model;

import android.util.Log;

import com.aldebaran.qi.Application;
import com.aldebaran.qi.CallError;
import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.proxies.ALMemory;
import com.aldebaran.qi.helper.proxies.ALMotion;
import com.aldebaran.qi.helper.proxies.ALSpeechRecognition;
import com.aldebaran.qi.helper.proxies.ALTextToSpeech;
import com.yipkaming.naoassistant.helper.DateHelper;
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
    private List<String> names;
    private List<String> relativesAndFriends;
    private List<String> reminderCommands;
    private Reminder pendingReminder;
    private float volumeLevel = (float) 0.5;   // set default value to be 50%
    private boolean isMakingReminder;
    private List<String> reminderType;

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
            ttsInit();
        }


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

//        initNameList();
        initRelativesList();
        initReminderCommandList();

        List<String> vocab = new ArrayList<>();
        vocab.add("Nao");               vocab.add("Yes please");
        vocab.add("Stop ASR");          vocab.add("Read notifications");
        vocab.add("How are you?");      vocab.add("OK");
        vocab.add("Any missed call");   vocab.add("Stop speech recognition service");
        vocab.add("Setup profile");     vocab.add("Make reminder");
        vocab.add("No");                vocab.add("Not really");
        vocab.add("I don't");           vocab.add("Thank you");
//        vocab.addAll(names);
        vocab.addAll(relativesAndFriends);
        vocab.addAll(reminderCommands);

        alSpeechRecognition.setLanguage(ENGLISH);
        alSpeechRecognition.setVocabulary(vocab, false);
        alSpeechRecognition.setParameter("Sensitivity", (float) 0.2);  // not effective
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
            ttsInit();
        }

        alSpeechRecognition.pause(true);

        String did = VerbalReminder.DID;
        String find_me = VerbalReminder.FIND_ME;
        String amf = VerbalReminder.ANY_MESSAGES_FROM;

        if(!"".equals(word) && (word.contains(did) || word.contains(find_me) || word.contains(amf))){
            Log.e(TAG, "relativesAndFriends" );
            String name = word;
            if(word.contains(did) && word.contains(find_me)){
                name = name.replace(did, "");
                name = name.replace(find_me, "");
            }else if (word.contains(amf)){
                name = name.replace(amf, "");
            }
            name = Character.toUpperCase(name.charAt(0)) + name.substring(1);
            SelectionHelper.getInstance().readingNotification(
                    NotificationMessage.findByName(Realm.getDefaultInstance(), name)
                    , getInstance()
                    , VerbalReminder.NOTHING_FROM+name);
//        }else if(names != null){
//            if(names.contains(word)) {
//                if (User.getInstance() == null) {
//                    String name = word;
//                    if (word.contains(VerbalReminder.YOU_CAN_CALL_ME)) {
//                        name = name.replace(VerbalReminder.YOU_CAN_CALL_ME, "");
//                    } else if (word.contains(VerbalReminder.CALL_ME)) {
//                        name = name.replace(VerbalReminder.CALL_ME, "");
//                    }
//
//                    String gender = names.indexOf(name) % 2 == 0 ? User.GENDERS[0] : User.GENDERS[1];
//
//                    Log.e(TAG, "name: " + name + ", " + gender);
//                    User user = new User();
//                    user.setName(name);
//                    user.setGender(gender);
//                    user.setCity("Hong Kong");
//                    user.save(Realm.getDefaultInstance());
//                    notificationGreeting();
//                    // health? sport? news? econ? weather?
//                }
//            }
        }else if(isMakingReminder){
            if(word.contains(DateHelper.TODAY) || word.contains(DateHelper.TOMORROW)
                    || word.contains(DateHelper.AM)  || word.contains(DateHelper.PM)
                    || word.contains(DateHelper.LATER)){
                makeReminder(word, true);
                alTextToSpeech.say("Ok, what event?");
            }else if(reminderType.contains(word)){
                makeReminder(word, false);
            }
        }else {
            switch (word) {
                case "Nao":
                    alTextToSpeech.say(VerbalReminder.HOW_CAN_I_HELP_YOU);
                    break;
                case "Yes please":
                    if (confirmAction != null) {
                        confirmAction.confirm();
                        setConfirmAction(null);
                    } else {
                        alTextToSpeech.say(VerbalReminder.GOOD);
                    }
//                SelectionHelper.read(this);
                    break;
                case "OK":
                    if (confirmAction != null) {
                        confirmAction.confirm();
                        setConfirmAction(null);
                    } else {
                        alTextToSpeech.say(VerbalReminder.GOOD);
                    }
//                SelectionHelper.read(this);
                    break;
                case "How are you?":
                    alTextToSpeech.say(VerbalReminder.I_AM_FINE_THANKYOU);
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
                case "Setup profile":
//                    alTextToSpeech.say(VerbalReminder.HOW_SHOULD_I_CALL_YOU);
//                    User.setInstance(null);
                    break;
                case "Make reminder":
                    alTextToSpeech.say(VerbalReminder.WHAT_TIME);
                    isMakingReminder = true;
                    break;
                case "No":
                    confirmAction = null;
                    alTextToSpeech.say("Ok then");
                    break;
                case "Not really":
                    confirmAction = null;
                    alTextToSpeech.say("Ok then");
                    break;
                case "Thank you":
                    alTextToSpeech.say("You are welcome"+ VerbalReminder.GREETING_WITH_NAME);
                    break;
                case "":
                    break;
                default:
                    alTextToSpeech.say(VerbalReminder.SORRY_I_DONT_UNDERSTAND);
                    break;
            }
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

    public void makeReminder(String content, boolean isTime){
        if(pendingReminder == null){
            pendingReminder = new Reminder();
            pendingReminder.setId(System.currentTimeMillis());
        }
        if(isTime){
            pendingReminder.setTime(content);
        }else{
            if(pendingReminder.getName() == null){
                pendingReminder.setName(content);
            }
        }

        if(pendingReminder.isComplete()){
            pendingReminder.scheduleNotification();
            pendingReminder = null;
            isMakingReminder = false;
            try {
                say("reminder is made successfully");
            } catch (Exception e) {
                e.printStackTrace();
            }
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
        say(VerbalReminder.INTRODUCTION);
//        if(isInit){
//            say(VerbalReminder.HOW_SHOULD_I_CALL_YOU);
//        }else {
            notificationGreeting();
//        }

    }

    private void notificationGreeting() throws Exception {
        say(VerbalReminder.GREETING_WITH_NAME);
        List<NotificationMessage> aboutToRead = NotificationMessage.findReadable(Realm.getDefaultInstance(), SelectionHelper.getImportanceThreshold());
        if(aboutToRead.isEmpty()){
            say(VerbalReminder.NO_NOTIFICATION);
        }else {
            say(VerbalReminder.getNotificationGreeting(aboutToRead.size()));
            confirmAction = new ReadNotification();
        }
    }

    private void ttsInit() throws Exception {
        alTextToSpeech = new ALTextToSpeech(getSession());
        alTextToSpeech.setParameter("defaultVoiceSpeed", (float) 800.0);
        alTextToSpeech.setParameter("pitchShift", (float) 1.15);
        alTextToSpeech.setVolume(volumeLevel); // 1.0 = set volume to 100%
    }


    private void initRelativesList() {
        relativesAndFriends = new ArrayList<>();
//        List<String> relativeList = VerbalReminder.getRelativeList();
//        relativesAndFriends.addAll(relativeList);

//        relativesAndFriends.add("Son");
//        relativesAndFriends.add("Daughter");
//        relativesAndFriends.add("Sister");
//        relativesAndFriends.add("Brother");
//        relativesAndFriends.add("Grandson");
//        relativesAndFriends.add("Granddaughter");
//        relativesAndFriends.add("Niece");
//        relativesAndFriends.add("Nephew");
//        relativesAndFriends.add("Husband");
//        relativesAndFriends.add("Wife");
//
//        List<String> list2 = new ArrayList<>();
//        List<String> list3 = new ArrayList<>();

        String any_msg_from = VerbalReminder.ANY_MESSAGES_FROM;
        String did = VerbalReminder.DID;
        String find_me = VerbalReminder.FIND_ME;

//        for(String relative : relativesAndFriends){
//            relative = relative.toLowerCase();
//            list2.add(any_msg_from+relative);
//            list3.add(did+relative+find_me);
//        }

        List<String> list4 = new ArrayList<>();
        List<String> list5 = new ArrayList<>();

        for(String name: Keyword.contacts){
            name = name.toLowerCase();
            list4.add(any_msg_from+name);
            list5.add(did+name+find_me);
        }

//        relativesAndFriends.addAll(list2);
//        relativesAndFriends.addAll(list3);
        relativesAndFriends.addAll(list4);
        relativesAndFriends.addAll(list5);
    }


    private void initReminderCommandList() {
        reminderCommands = new ArrayList<>();

        reminderType = new ArrayList<>();
        reminderType.add("Family");
        reminderType.add("Family gathering");
        reminderType.add("Family dinner");
        reminderType.add("Family day");
        reminderType.add("Meeting");
        reminderType.add("Doctor meeting");
        reminderType.add("Work");
        reminderType.add("Scheduling");
        reminderType.add("Exercise");
        reminderType.add("Reminder");

        reminderCommands.addAll(reminderType);


        reminderCommands.add(DateHelper.TODAY+DateHelper.ONE+VerbalReminder.SPACE+DateHelper.AM);
        reminderCommands.add(DateHelper.TODAY+DateHelper.TWO+VerbalReminder.SPACE+DateHelper.AM);
        reminderCommands.add(DateHelper.TODAY+DateHelper.THREE+VerbalReminder.SPACE+DateHelper.AM);
        reminderCommands.add(DateHelper.TODAY+DateHelper.FOUR+VerbalReminder.SPACE+DateHelper.AM);
        reminderCommands.add(DateHelper.TODAY+DateHelper.FIVE+VerbalReminder.SPACE+DateHelper.AM);
        reminderCommands.add(DateHelper.TODAY+DateHelper.SIX+VerbalReminder.SPACE+DateHelper.AM);
        reminderCommands.add(DateHelper.TODAY+DateHelper.SEVEN+VerbalReminder.SPACE+DateHelper.AM);
        reminderCommands.add(DateHelper.TODAY+DateHelper.EIGHT+VerbalReminder.SPACE+DateHelper.AM);
        reminderCommands.add(DateHelper.TODAY+DateHelper.NINE+VerbalReminder.SPACE+DateHelper.AM);
        reminderCommands.add(DateHelper.TODAY+DateHelper.TEN+VerbalReminder.SPACE+DateHelper.AM);
        reminderCommands.add(DateHelper.TODAY+DateHelper.ELEVEN+VerbalReminder.SPACE+DateHelper.AM);
        reminderCommands.add(DateHelper.TODAY+DateHelper.TWELVE+VerbalReminder.SPACE+DateHelper.AM);

        reminderCommands.add(DateHelper.TODAY+DateHelper.ONE+VerbalReminder.SPACE+DateHelper.PM);
        reminderCommands.add(DateHelper.TODAY+DateHelper.TWO+VerbalReminder.SPACE+DateHelper.PM);
        reminderCommands.add(DateHelper.TODAY+DateHelper.THREE+VerbalReminder.SPACE+DateHelper.PM);
        reminderCommands.add(DateHelper.TODAY+DateHelper.FOUR+VerbalReminder.SPACE+DateHelper.PM);
        reminderCommands.add(DateHelper.TODAY+DateHelper.FIVE+VerbalReminder.SPACE+DateHelper.PM);
        reminderCommands.add(DateHelper.TODAY+DateHelper.SIX+VerbalReminder.SPACE+DateHelper.PM);
        reminderCommands.add(DateHelper.TODAY+DateHelper.SEVEN+VerbalReminder.SPACE+DateHelper.PM);
        reminderCommands.add(DateHelper.TODAY+DateHelper.EIGHT+VerbalReminder.SPACE+DateHelper.PM);
        reminderCommands.add(DateHelper.TODAY+DateHelper.NINE+VerbalReminder.SPACE+DateHelper.PM);
        reminderCommands.add(DateHelper.TODAY+DateHelper.TEN+VerbalReminder.SPACE+DateHelper.PM);
        reminderCommands.add(DateHelper.TODAY+DateHelper.ELEVEN+VerbalReminder.SPACE+DateHelper.PM);
        reminderCommands.add(DateHelper.TODAY+DateHelper.TWELVE+VerbalReminder.SPACE+DateHelper.PM);

        reminderCommands.add(DateHelper.TOMORROW+DateHelper.ONE+VerbalReminder.SPACE+DateHelper.AM);
        reminderCommands.add(DateHelper.TOMORROW+DateHelper.TWO+VerbalReminder.SPACE+DateHelper.AM);
        reminderCommands.add(DateHelper.TOMORROW+DateHelper.THREE+VerbalReminder.SPACE+DateHelper.AM);
        reminderCommands.add(DateHelper.TOMORROW+DateHelper.FOUR+VerbalReminder.SPACE+DateHelper.AM);
        reminderCommands.add(DateHelper.TOMORROW+DateHelper.FIVE+VerbalReminder.SPACE+DateHelper.AM);
        reminderCommands.add(DateHelper.TOMORROW+DateHelper.SIX+VerbalReminder.SPACE+DateHelper.AM);
        reminderCommands.add(DateHelper.TOMORROW+DateHelper.SEVEN+VerbalReminder.SPACE+DateHelper.AM);
        reminderCommands.add(DateHelper.TOMORROW+DateHelper.EIGHT+VerbalReminder.SPACE+DateHelper.AM);
        reminderCommands.add(DateHelper.TOMORROW+DateHelper.NINE+VerbalReminder.SPACE+DateHelper.AM);
        reminderCommands.add(DateHelper.TOMORROW+DateHelper.TEN+VerbalReminder.SPACE+DateHelper.AM);
        reminderCommands.add(DateHelper.TOMORROW+DateHelper.ELEVEN+VerbalReminder.SPACE+DateHelper.AM);
        reminderCommands.add(DateHelper.TOMORROW+DateHelper.TWELVE+VerbalReminder.SPACE+DateHelper.AM);

        reminderCommands.add(DateHelper.TOMORROW+DateHelper.ONE+VerbalReminder.SPACE+DateHelper.PM);
        reminderCommands.add(DateHelper.TOMORROW+DateHelper.TWO+VerbalReminder.SPACE+DateHelper.PM);
        reminderCommands.add(DateHelper.TOMORROW+DateHelper.THREE+VerbalReminder.SPACE+DateHelper.PM);
        reminderCommands.add(DateHelper.TOMORROW+DateHelper.FOUR+VerbalReminder.SPACE+DateHelper.PM);
        reminderCommands.add(DateHelper.TOMORROW+DateHelper.FIVE+VerbalReminder.SPACE+DateHelper.PM);
        reminderCommands.add(DateHelper.TOMORROW+DateHelper.SIX+DateHelper.PM);
        reminderCommands.add(DateHelper.TOMORROW+DateHelper.SEVEN+VerbalReminder.SPACE+DateHelper.PM);
        reminderCommands.add(DateHelper.TOMORROW+DateHelper.EIGHT+VerbalReminder.SPACE+DateHelper.PM);
        reminderCommands.add(DateHelper.TOMORROW+DateHelper.NINE+VerbalReminder.SPACE+DateHelper.PM);
        reminderCommands.add(DateHelper.TOMORROW+DateHelper.TEN+VerbalReminder.SPACE+DateHelper.PM);
        reminderCommands.add(DateHelper.TOMORROW+DateHelper.ELEVEN+VerbalReminder.SPACE+DateHelper.PM);
        reminderCommands.add(DateHelper.TOMORROW+DateHelper.TWELVE+VerbalReminder.SPACE+DateHelper.PM);


        reminderCommands.add(DateHelper.ONE_UPPER+VerbalReminder.SPACE+DateHelper.LOWER_MINUTE+DateHelper.LATER);
        reminderCommands.add(DateHelper.TWO_UPPER+VerbalReminder.SPACE+DateHelper.LOWER_MINUTES+DateHelper.LATER);
        reminderCommands.add(DateHelper.THREE_UPPER+VerbalReminder.SPACE+DateHelper.LOWER_MINUTES+DateHelper.LATER);
        reminderCommands.add(DateHelper.FOUR_UPPER+VerbalReminder.SPACE+DateHelper.LOWER_MINUTES+DateHelper.LATER);
        reminderCommands.add(DateHelper.FIVE_UPPER+VerbalReminder.SPACE+DateHelper.LOWER_MINUTES+DateHelper.LATER);
        reminderCommands.add(DateHelper.SIX_UPPER+VerbalReminder.SPACE+DateHelper.LOWER_MINUTES+DateHelper.LATER);
        reminderCommands.add(DateHelper.SEVEN_UPPER+VerbalReminder.SPACE+DateHelper.LOWER_MINUTES+DateHelper.LATER);
        reminderCommands.add(DateHelper.EIGHT_UPPER+VerbalReminder.SPACE+DateHelper.LOWER_MINUTES+DateHelper.LATER);
        reminderCommands.add(DateHelper.NINE_UPPER+VerbalReminder.SPACE+DateHelper.LOWER_MINUTES+DateHelper.LATER);
        reminderCommands.add(DateHelper.TEN_UPPER+VerbalReminder.SPACE+DateHelper.LOWER_MINUTES+DateHelper.LATER);
        reminderCommands.add(DateHelper.ELEVEN+VerbalReminder.SPACE+DateHelper.LOWER_MINUTES+DateHelper.LATER);
        reminderCommands.add(DateHelper.TWELVE_UPPER+VerbalReminder.SPACE+DateHelper.LOWER_MINUTES+DateHelper.LATER);
        reminderCommands.add(DateHelper.FIFTEEN_UPPER+VerbalReminder.SPACE+DateHelper.LOWER_MINUTES+DateHelper.LATER);
        reminderCommands.add(DateHelper.TWENTY_UPPER+VerbalReminder.SPACE+DateHelper.LOWER_MINUTES+DateHelper.LATER);
        reminderCommands.add(DateHelper.TWENTY_FIVE_UPPER+VerbalReminder.SPACE+DateHelper.LOWER_MINUTES+DateHelper.LATER);
        reminderCommands.add(DateHelper.THIRTY_UPPER+VerbalReminder.SPACE+DateHelper.LOWER_MINUTES+DateHelper.LATER);
        reminderCommands.add(DateHelper.THIRTY_FIVE_UPPER+VerbalReminder.SPACE+DateHelper.LOWER_MINUTES+DateHelper.LATER);
        reminderCommands.add(DateHelper.FORTY_UPPER+VerbalReminder.SPACE+DateHelper.LOWER_MINUTES+DateHelper.LATER);
        reminderCommands.add(DateHelper.FORTY_FIVE_UPPER+VerbalReminder.SPACE+DateHelper.LOWER_MINUTES+DateHelper.LATER);
        reminderCommands.add(DateHelper.FIFTY_UPPER+VerbalReminder.SPACE+DateHelper.LOWER_MINUTES+DateHelper.LATER);
        reminderCommands.add(DateHelper.FIFTY_FIVE_UPPER+VerbalReminder.SPACE+DateHelper.LOWER_MINUTES+DateHelper.LATER);

        reminderCommands.add(DateHelper.ONE_UPPER+VerbalReminder.SPACE+DateHelper.LOWER_HOUR+DateHelper.LATER);
        reminderCommands.add(DateHelper.TWO_UPPER+VerbalReminder.SPACE+DateHelper.LOWER_HOURS+DateHelper.LATER);
        reminderCommands.add(DateHelper.THREE_UPPER+VerbalReminder.SPACE+DateHelper.LOWER_HOURS+DateHelper.LATER);


    }


    private void initNameList(){
        names = new ArrayList<>();   // names used to setup user profile
        names.add("Michael");   names.add("Linda");     names.add("Robert");    names.add("Patricia");
//        names.add("John");      names.add("Susan");     names.add("David");     names.add("Deborah");
//        names.add("William");   names.add("Barbara");   names.add("Richard");   names.add("Debra");
        names.add("James");     names.add("Mary");      names.add("Thomas");    names.add("Karen");
//        names.add("Mark");      names.add("Nancy");     names.add("Charles");   names.add("Donna");
//        names.add("Steven");    names.add("Cynthia");   names.add("Gary");      names.add("Sandra");
//        names.add("Joseph");    names.add("Pamela");    names.add("Donald");    names.add("Sharon");
//        names.add("Ronald");    names.add("Kathleen");  names.add("Kenneth");   names.add("Carol");
//        names.add("Paul");      names.add("Diane");     names.add("Larry");     names.add("Brenda");
//        names.add("Daniel");    names.add("Cheryl");    names.add("Stephen");   names.add("Janet");
//        names.add("Dennis");    names.add("Elizabeth"); names.add("Timothy");   names.add("Kathy");
//        names.add("Edward");    names.add("Margaret");  names.add("Jeffrey");   names.add("Janice");
        names.add("George");    names.add("Carolyn");   names.add("Rick");      names.add("Joanna");
        names.add("Cyrus");     names.add("Cat");

        List<String> nameList2 = new ArrayList<>();
        List<String> nameList3 = new ArrayList<>();
        String you_can_call_me = VerbalReminder.YOU_CAN_CALL_ME;
        String call_me = VerbalReminder.CALL_ME;
        for(String name : names){
            nameList2.add(you_can_call_me+name);
            nameList3.add(call_me+name);
        }
        names.addAll(nameList2);
        names.addAll(nameList3);
        names = null;
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

    public float getVolumeLevel() {
        return volumeLevel;
    }

    public void setVolumeLevel(float volumeLevel) {
        this.volumeLevel = volumeLevel;
    }
}
