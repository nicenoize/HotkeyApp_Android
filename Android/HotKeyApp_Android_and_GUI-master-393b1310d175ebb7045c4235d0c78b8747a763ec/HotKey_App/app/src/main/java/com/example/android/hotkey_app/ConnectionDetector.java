package com.example.android.hotkey_app;

import android.app.Service;
import android.content.Context;
import android.net.ConnectivityManager;

/**
 * ConnectionDetector
 * @author Timm Dunker, Sebastian Herrmann
 * #############################
 * This class tests wifi and working internet connection
 */
public class ConnectionDetector {

    /** Activities context */
    private Context context;

    /**
     * Constructor: Creates a new Instance of this class with a given context
     * @param context context of activity that instantiated this class
     */
    public ConnectionDetector(Context context){
        this.context = context;
    }

    /**
     * Command: Checks for  wifi and working internet connection
     * @return true if wifi and internet connection working else false
     */
    public boolean isWifi(){
        final ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Service.CONNECTIVITY_SERVICE);
        if(connectivityManager != null){
            final android.net.NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if(wifi != null && wifi.isConnectedOrConnecting()){
                    return true;
            } else {
                return false;
            }
        }
        return false;
    }

}
