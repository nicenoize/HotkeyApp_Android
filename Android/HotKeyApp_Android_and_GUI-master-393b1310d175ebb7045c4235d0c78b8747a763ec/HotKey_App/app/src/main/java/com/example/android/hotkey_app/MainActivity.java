package com.example.android.hotkey_app;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * MainActivity
 * #############################
 * @author Timm Dunker, Sebastian Herrmann
 * #############################
 * This class provides HotKey App's startscreen
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    /** holds all the GUI elements for this activity  */
    private MainGui mainGui;

    /** Activitie's context  */
    public static Context context;

    /** Connection client  */
    private ClientMain clientMain;

    /**
     * Command: Activity Initialization, sets activity view to given layout
     * and sets up GUI for this view
     * @param savedInstanceState saved State of previous instance
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getApplicationContext();
        setContentView(R.layout.activity_main);
        mainGui = new MainGui(this);
        mainGui.initGui();
    }

    /**
     * Command: specifies the activities options menu
     * @param menu menu that should be inflated
     * @return true if menu was inflated successfully
     */
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        final MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation_menu, menu);
        return true;
    }

    /**
     * Command: Handles user interaction on options menu
     * @param item selected menu item
     * @return true if item was handled successfully
     */
    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if(mainGui.getToggle().onOptionsItemSelected(item)) return true;
        return onOptionsItemSelected(item);
    }

    /**
     * Command: Handles user interaction on navigation menu
     * @param item item that was clicked
     * @return true if item was handled successfully
     */
    @Override
    public boolean onNavigationItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_about: {
                AboutActivity.launch(this);
                break;
            }
            case R.id.nav_settings: {
                startActivity(new Intent(WifiManager.ACTION_PICK_WIFI_NETWORK));
                break;
            }
            case R.id.nav_quit: {
                finish();
            }
        }
        //close navigation drawer
        mainGui.getDrawerLayout().closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Command: Starts a new client connection if wifi and internet connection established
     * calls client constructor with postTaskListener and user entered server address
     */
    public void tryServerConnection() {
        mainGui.toggleUI();
        final ConnectionDetector connectionDetector = new ConnectionDetector(this);
        if (connectionDetector.isWifi()) {
            PostTaskListener<Integer> postTaskListener = new PostTaskListener<Integer>() {
                @Override
                public void onPostTask(int result) {
                    postServerConnection(result);
                    clientMain.cancel(true);
                }
            };
            // start new client
            clientMain = new ClientMain(postTaskListener, mainGui.getManualAddress());
            clientMain.execute();
        } else { postServerConnection(R.string.errorWifi);}
    }

    /**
     * Command: Gets called when activity will start interacting with the user
     * updates GUI
     */
    @Override
    protected void onResume(){
        super.onResume();
        mainGui.setConnectingStatus(true);
        mainGui.toggleUI();
    }

    /**
     * Command: Gets called after server connection finished
     * updates GUI and sends feedback tu UI if error occurred
     */
    private void postServerConnection(int message){
        mainGui.toggleUI();
        mainGui.sendToast(message);
    }
}