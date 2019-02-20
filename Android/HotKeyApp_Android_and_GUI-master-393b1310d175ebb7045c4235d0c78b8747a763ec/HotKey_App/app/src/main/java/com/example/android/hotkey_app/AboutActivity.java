package com.example.android.hotkey_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * HotKey Android App
 * #############################
 * @author Timm Dunker
 * #############################
 * This class provides the About Activity
 */
public class AboutActivity extends AppCompatActivity {

    /**
     * Command: Activity Initialization, sets activity view to given layout
     * @param savedInstanceState saved State of previous instance
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_main);
    }

    /**
     * Command: Launches the About Activity
     * @param context context of activity that wants to start the new activity
     */
    public static void launch(final Context context){
        final Intent intent = new Intent(context, AboutActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
