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

import com.aldebaran.qi.helper.proxies.ALTextToSpeech;
import com.yipkaming.naoassistant.R;
import com.yipkaming.naoassistant.activity.AssistantActivity;
import com.yipkaming.naoassistant.model.Config;
import com.yipkaming.naoassistant.model.Naoqi;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConnectionFragment extends Fragment {


    private static final String TAG = Config.getSimpleName(ConnectionFragment.class);

    View view;
    EditText ip;
    Button connect;

    private Naoqi naoqi;
    String ipAddr;

    private OnConnectionListener onConnectionListener;

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
        setupUIListener();
    }

    private void setupUIListener() {
        if(naoqi != null && naoqi.isRunning()){
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
        naoqi = Naoqi.getInstance();
        naoqi.init("tcp://"+ipAddr+":9559");
        if(naoqi.isRunning()){
            Log.e("connect: ", "!!!!!!!!!!!!!!!!!" );
            setUIItems(naoqi.isRunning());
            try {
                ALTextToSpeech tts = new ALTextToSpeech(naoqi.getSession());
                tts.say("Connected");
            } catch (Exception e) {
                e.printStackTrace();
            }

            Intent intent = new Intent(getContext(), AssistantActivity.class);
            startActivity(intent);
//            onConnectionListener.onConnected();
        }else {
            Log.e("connect: ", "*****************" );
        }
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
}
