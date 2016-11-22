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
import com.yipkaming.naoassistant.R;
import com.yipkaming.naoassistant.activity.AssistantActivity;
import com.yipkaming.naoassistant.helper.KeyboardHelper;
import com.yipkaming.naoassistant.model.Config;
import com.yipkaming.naoassistant.model.Nao;

public class ConnectionFragment extends Fragment {

    private static final String TAG = Config.getSimpleName(ConnectionFragment.class);
    private static ConnectionFragment instance;

    private Nao nao;
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
        nao = Nao.getInstance();
        setupUIListener();
    }

    private void setupUIListener() {
        if(nao.isRunning()){
            setUIItems(nao.isRunning());
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
        // todo add progress dialog
        /*
        new MaterialDialog.Builder(this)
            .title(R.string.progress_dialog)
            .content(R.string.please_wait)
            .progress(true, 0)
            .show();
         */
        nao.init("tcp://"+ipAddr+":9559");
        boolean connected = nao.isRunning();
        if(connected){
            Log.e("connect: ", "!!!!!!!!!!!!!!!!!" );
            setUIItems(connected);
            try {
                nao.say("Connected");
            } catch (Exception e) {
                e.printStackTrace();
            }

            Intent intent = new Intent(getContext(), AssistantActivity.class);
            startActivity(intent);
            instance = this;
            KeyboardHelper.closeKeyboardFromFragment(getContext(), view);
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

    private void setUIItems(boolean isConnected){
        String ipStr = nao.get_IP();
        if( ipStr != null){
            ipStr = ipStr.substring(6);
        }
        ip.setText(ipStr);
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
        void onConnected();
    }


    public static ConnectionFragment getInstance() {
        if(instance == null){
            instance = new ConnectionFragment();
        }
        return instance;
    }
}
