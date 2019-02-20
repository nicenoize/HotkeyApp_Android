package com.example.android.hotkey_app;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import java.util.HashMap;

/**
 * SecondGui
 * #############################
 * @author Timm Dunker, Sebastian Herrmann
 * #############################
 * This class provides HotKey App's UI for second Activity
 */
public class SecondGui {

    /** SecondActivity's context */
    private SecondActivity context;

    /** Toolbar title */
    private TextView tvMainTitle;

    /** View that will hold all dynamic content */
    private RecyclerView recyclerView;

    /** Adapter that will provide the content for the recycler view */
    private RecyclerAdapter adapter;

    /**
     * Constructor: Creates a new Instance of this class with given context
     * @param context context of SecondActivity that instantiated the class
     */
    public SecondGui(final SecondActivity context){
        this.context = context;
    }

    /**
     * Command: Gets and sets up the UI elements for the SecondActivity
     * binds UI elements to their xml representations
     */
    public void initGui() {
        final Toolbar toolbar = context.findViewById(R.id.my_toolbar);
        tvMainTitle = toolbar.findViewById(R.id.toolbar_title);
        context.setSupportActionBar(toolbar);
        context.getSupportActionBar().setDisplayShowTitleEnabled(false);
        recyclerView = context.findViewById(R.id.recyclerView);
    }

    /**
     * Command: Creates a buttons with recycler adapter based on hotkeymap
     * inside the recycleview
     */
    public void setButtons(HashMap<String,String> hotKeyMap) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new RecyclerAdapter(hotKeyMap);
        recyclerView.setAdapter(adapter);
    }

    /**
     * Command: Sets the toolbar title to the given value
     * @oarams title new toolbar title
     */
    public void setMainTitle(String title){
        tvMainTitle.setText(title);
    }

}
