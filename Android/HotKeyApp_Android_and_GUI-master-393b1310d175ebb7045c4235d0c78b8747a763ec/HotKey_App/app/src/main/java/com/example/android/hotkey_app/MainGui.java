package com.example.android.hotkey_app;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


/**
 * MainGui
 * #############################
 * @author Timm Dunker, Sebastian Herrmann
 * #############################
 * This class provides the UI for HotKey App's startscreen
 */
public class MainGui {

    /** MainActivity's context */
    private MainActivity context;

    /** holds true if in connections process */
    private boolean connecting = false;

    /** UI elements necessary for startscreen */
    private LinearLayout linearLayout;
    private TextView mainText;
    private ProgressBar progressBar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private EditText serverAddress;

    /**
     * Constructor: Creates a new Instance of this class with given context
     * @param context context of MainActivity that instantiated the class
     */
    public MainGui(final MainActivity context){
        this.context = context;
    }

    /**
     * Command: Gets and sets up the UI elements for the startscreen
     * binds UI elements to their xml representations
     */
    public void initGui(){
        final Button connectButton = context.findViewById(R.id.btnConnect);
        final NavigationView navigationView = context.findViewById(R.id.nav_view);
        final Toolbar mToolbar = context.findViewById(R.id.nav_action);
        linearLayout = context.findViewById(R.id.layoutConnect);
        mainText = context.findViewById(R.id.tvMain);
        progressBar = context.findViewById(R.id.mainProgressbar);
        serverAddress = context.findViewById(R.id.etServerAddress);
        mDrawerLayout = context.findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(context, mDrawerLayout, R.string.open, R.string.close);

        setUpToolbar(mToolbar, navigationView);
        setUpMenu();
        setUpConnectButton(connectButton);
    }

    /**
     * Command: toggles UI Elements depending on connecting status
     */
    public void toggleUI(){
        connecting = !connecting;
        if(connecting){
            mainText.setText(R.string.serverConnect);
            mainText.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }else {
            mainText.setText(R.string.notConnected);
            mainText.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }

    /**
     * Command: Sets the connectingStatus for this MainGui
     */
    public void setConnectingStatus(boolean connecting){
        this.connecting = connecting;
    }

    /**
     * Command: Gets the drawerLayout for this MainGui
     * @return the drawer layout
     */
    public DrawerLayout getDrawerLayout(){
        return this.mDrawerLayout;
    }

    /**
     * Command: Gets menu toggle for this MainGui
     * @return the toggleButton instance
     */
    public ActionBarDrawerToggle getToggle(){
        return this.mToggle;
    }

    /**
     * Command: Gets the entered server address by user
     * @return entered server address, empty address if nothing was entered
     */
    public String getManualAddress(){
        context.findViewById(R.id.etServerAddress);
        return serverAddress.getText().toString();
    }

    /**
     * Command: Sends a Toast (user feedback) to the UI thread
     */
    public void sendToast(int message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * Command: Sets up the toolbar
     * changes default toolbar title
     * connects navigation with it's listener
     */
    private void setUpToolbar(Toolbar mToolbar, NavigationView navigationView){
        context.setSupportActionBar(mToolbar);
        context.getSupportActionBar().setTitle(R.string.app_name);
        context.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(context);
    }

    /**
     * Command: Sets up drawer toggle menu
     */
    private void setUpMenu(){
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
    }

    /**
     * Command: Sets on click listener for the connection button
     * on click tries server connection
     */
    private void setUpConnectButton(Button connectButton){
        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.tryServerConnection();
            }
        });
    }
}
