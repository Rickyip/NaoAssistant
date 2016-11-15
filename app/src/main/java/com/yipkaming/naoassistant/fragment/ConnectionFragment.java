package com.yipkaming.naoassistant.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.aldebaran.qi.helper.proxies.ALTextToSpeech;
import com.yipkaming.naoassistant.R;
import com.yipkaming.naoassistant.activity.AssistantActivity;
import com.yipkaming.naoassistant.model.Config;
import com.yipkaming.naoassistant.model.Naoqi;


public class ConnectionFragment extends Fragment {

    private static final String TAG = Config.getSimpleName(ConnectionFragment.class);

    private static ConnectionFragment instance;

    private Naoqi naoqi;

    private OnConnectionListener onConnectionListener;

    View view;
    EditText ip;
    Button connect;
    String ipAddr;

    public ConnectionFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_connection, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ip = (EditText) view.findViewById(R.id.ip);
        connect = (Button) view.findViewById(R.id.connect);
        naoqi = Naoqi.getInstance();
        setupUIListener();
    }

    private void setupUIListener() {
        if(naoqi.isRunning()){
            setUIItems(naoqi.isRunning());
        }else {
            connect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    makeConnection();
                }
            });
        }
    }

    private void makeConnection() {
        ipAddr = ip.getText().toString();
        boolean connected = naoqi.isRunning();
        // todo add progress dialog
        /*
        new MaterialDialog.Builder(this)
            .title(R.string.progress_dialog)
            .content(R.string.please_wait)
            .progress(true, 0)
            .show();
         */
        if(!connected){
            naoqi.init("tcp://"+ipAddr+":9559");
            connected = naoqi.isRunning();
            if(connected){
                Log.e("connect: ", "!!!!!!!!!!!!!!!!!" );
                setUIItems(connected);
                try {
                    ALTextToSpeech tts = new ALTextToSpeech(naoqi.getSession());
                    tts.say("Connected");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(getContext(), AssistantActivity.class);
                startActivity(intent);
                instance = this;
//            onConnectionListener.onConnected();
            }else {
                Log.e("connect: ", "*****************" );
                new MaterialDialog.Builder(getContext())
                        .title(R.string.Connection_failed)
                        .content(R.string.Connection_failed_content)
                        .positiveText(R.string.OK)
                        .show();
            }
        }
//        else {
//            setUIItems(connected);
//            Log.e(TAG, "makeConnection: " );
//        }
    }

    private void setUIItems(boolean isConnected){
        ip.setEnabled(!isConnected);
        connect.setText(R.string.connected);
        connect.setEnabled(!isConnected);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnConnectionListener) {
            onConnectionListener = (OnConnectionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
    public interface OnConnectionListener {
        // TODO: Update argument type and name
        void onConnected();
    }


    public static ConnectionFragment getInstance() {
        if(instance == null){
            instance = new ConnectionFragment();
        }
        return instance;
    }
}
