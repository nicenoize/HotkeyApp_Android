package com.example.android.hotkey_app;

/**
 * PostTaskListener
 * #############################
 * @author Timm Dunker, Sebastian Herrmann
 * #############################
 * This class provides a listener for the client connection
 */
public interface PostTaskListener<Voids> {
    /**
     * Gets called when client is finished
     * @param result reference to /values/strings id's
     */
    void onPostTask(int result);
}
