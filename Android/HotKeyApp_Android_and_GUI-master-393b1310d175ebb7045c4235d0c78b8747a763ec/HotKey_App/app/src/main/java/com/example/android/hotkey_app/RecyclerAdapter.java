package com.example.android.hotkey_app;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import communicationObjects.Hotkey;

/**
 * RecyclerAdapter
 * #############################
 * @author Timm Dunker, Sebastian Herrmann
 * #############################
 * This class provides the recycler adapter for the second activity
 * to build the hotkey buttons dynamically
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewholder>{

    /** holds keys of given hotkey map */
    final private List<String> keys = new ArrayList<>();

    /** holds values of given hotkey map */
    final private List<String> values = new ArrayList<>();

    /**
     * Constructor: Creates a new Instance of this class with given hotKeyMap
     * and sets keys and values lists with hotkeymap data
     * @param hotKeyMap that was sent as server response
     */
    RecyclerAdapter(final HashMap<String, String> hotKeyMap) {
        this.keys.addAll(hotKeyMap.keySet());
        this.values.addAll(hotKeyMap.values());
    }

    /**
     * Command: gets the number of items (buttons) that need to be created
     * @return number of buttons (one button for each key)
     */
    @Override
    public int getItemCount() { return keys.size(); }

    /**
     * Command: Creates a holder for the view where the buttons will be populated
     * @return the created view holder
     */
    @Override
    public MyViewholder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        return new MyViewholder(view);
    }

    /**
     * Command: binds buttons inside view holder with data
     */
    @Override
    public void onBindViewHolder(final MyViewholder holder, final int position) {
        holder.buttonSendHotKey.setText(keys.get(position));
        holder.buttonSendHotKey.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                final Hotkey hotkey = new Hotkey(keys.get(position), values.get(position));
                sendCommand(hotkey);
            }
        });
    }

    /**
     * MyViewholder
     * caches objects inside view holder
     */
    public static class MyViewholder extends RecyclerView.ViewHolder{
        final Button buttonSendHotKey;
        private MyViewholder(View itemView) {
            super(itemView);
            buttonSendHotKey = itemView.findViewById(R.id.btnHotkey);
        }
    }

    /**
     * Command: creates a new client and submits pressed hotkey
     * executes client
     */
    private void sendCommand(final Hotkey hotkey){
        final ClientMain client = new ClientMain(hotkey);
        client.execute();
    }

}
