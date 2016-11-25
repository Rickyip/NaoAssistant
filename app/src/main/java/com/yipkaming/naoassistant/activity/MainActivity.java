package com.yipkaming.naoassistant.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yipkaming.naoassistant.R;
import com.yipkaming.naoassistant.fragment.ConnectionFragment;
import com.yipkaming.naoassistant.model.Config;

public class MainActivity extends AppCompatActivity implements ConnectionFragment.OnConnectionListener {

    private static final String TAG = Config.getSimpleName(MainActivity.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void initFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.connectFragment, ConnectionFragment.getInstance())
                .commit();
    }

    @Override
    public void onConnected() {
        startAssistant();
    }

    private void startAssistant() {
        Intent intent = new Intent(this, AssistantActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initFragment();
//        onConnected();
    }
}
