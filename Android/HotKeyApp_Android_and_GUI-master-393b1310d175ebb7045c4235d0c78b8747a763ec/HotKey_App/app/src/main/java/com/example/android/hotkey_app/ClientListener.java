package com.example.android.hotkey_app;

import android.content.Intent;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import communicationObjects.HotkeyMap;

public class ClientListener extends Listener {

    /** client that sends map request */
    private Client client;

    /**
     * Command: receive HotkeyMap from server
     * if response is HotkeyMap then start a new activity and end client connection
     * @param connection server response
     * @param object object received from server response
     */
    @Override
    public void received(final Connection connection, final Object object){
        if(object instanceof HotkeyMap){
            final HotkeyMap hotKeyMap = (HotkeyMap) object;
            launchActivity(hotKeyMap);
            endClientConnection();
        }
    }

    /**
     * Command: Launch SecondActivity and pass received data
     * @param hotkeyMap received hotkeyMap from server
     */
    private void launchActivity(final HotkeyMap hotkeyMap){
        final Intent intent = new Intent(MainActivity.context, SecondActivity.class);
        intent.putExtra("HotkeyMapKeys", hotkeyMap.getAllKeys());
        intent.putExtra("HotkeyMapProgrammname", hotkeyMap.getProgrammname());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        MainActivity.context.startActivity(intent);
    }

    /**
     * Command: Sets passed client to client of this class
     * @param client client connection
     */
    public void setClient(Client client){
        this.client = client;
    }

    /**
     * Command: Ends client connection
     */
    private void endClientConnection(){
        client.stop();
    }
}