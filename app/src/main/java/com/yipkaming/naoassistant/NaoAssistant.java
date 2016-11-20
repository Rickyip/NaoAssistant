package com.yipkaming.naoassistant;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;
import com.yipkaming.naoassistant.model.Config;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Yip on 18/10/2016.
 */

public class NaoAssistant extends Application {

    private static final String TAG = Config.getSimpleName(NaoAssistant.class);

    private static NaoAssistant context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        initRealm();

        // comment below if you don't need to debug the realm db
        initStetho();

//        init();

    }

    private void initRealm() {

        // use this block during development only
        RealmConfiguration configuration = new RealmConfiguration.Builder(getApplicationContext())
                .schemaVersion(19)

                .deleteRealmIfMigrationNeeded()
                .build();

        //todo un-comment this for production
        // RealmConfiguration configuration = new RealmConfiguration.Builder(getApplicationContext()).build();

        Realm.setDefaultConfiguration(configuration);
    }

    private void initStetho() {
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(
                                Stetho.defaultDumperPluginsProvider(this)
                        )
                        .enableWebKitInspector(
                                RealmInspectorModulesProvider.builder(this).build()
                        )
                        .build()
        );
    }

    public static NaoAssistant getContext() {
        return context;
    }


}
