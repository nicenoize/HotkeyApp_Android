package com.example.android.hotkey_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import java.util.HashMap;

/**
 * SecondActivity
 * #############################
 * @author Timm Dunker, Sebastian Herrmann
 * #############################
 * This class provides the SecondActivity
 */

public class SecondActivity extends AppCompatActivity {

    /** Activitie's context */
    private SecondGui secondGui;

    /**
     * Command: Activity Initialization, sets activity view to given layout
     * sets up GUI for this view
     * handles the intent
     * @param savedInstanceState saved State of previous instance
     */
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);
        secondGui = new SecondGui(this);
        secondGui.initGui();
        handleIntent();
    }

    /**
     * Command: gets with the intent passed data
     * sets toolbar title to programm name
     * sets button for received hotkey map
     */
    public void handleIntent(){
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null){
            final String programmName = (String) extras.get("HotkeyMapProgrammname");
            final HashMap<String, String> hotKetMap = (HashMap<String,String>) extras.get("HotkeyMapKeys");
            secondGui.setMainTitle(programmName);
            secondGui.setButtons(hotKetMap);
        }
    }

}
