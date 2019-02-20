package com.example.android.hotkey_app;

import android.os.AsyncTask;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import communicationObjects.Hotkey;
import communicationObjects.MapRequest;

/**
 * ClientMain
 * @author Timm Dunker, Sebastian Herrmann
 * #############################
 * This class connects to the server and either submits a pressed HotKey or a MapRequest
 */
public class ClientMain extends AsyncTask<Void, Void, Void>{

    /** Entered server address submitted by user */
    private String serverAddress;

    /** Submits user feedback to Activity */
    private PostTaskListener<Integer> postTaskListener;

    /** Listenes for packages */
    private ClientListener clientListener;

    /** Pressed hotkey */
    private Hotkey hotKey;

    /** Marks that client should wait for response before closing connection */
    private boolean waitForResponse = false;

    /** connection client */
    public Client client;

    /**
     * Constructor: Creates a new Instance of this class with a given postTaskListener
     * and serverAddress entered by the user
     * @param postTaskListener waits for task to finish
     * @param serverAddress manually entered server by user
     */
    public ClientMain(final PostTaskListener<Integer> postTaskListener, final String serverAddress){
        this.postTaskListener = postTaskListener;
        this.serverAddress = serverAddress;
    }

    /**
     * Constructor: Creates a new Instance of this class with a given hotkey
     * @param hotkey pressed hotkey
     */
    public ClientMain(Hotkey hotkey){
        this.hotKey = hotkey;
    }

    /**
     * Command: works asynchronous
     * tries entered server address by user
     * if not submitted looks for a server address inside the network
     * either sends a maprequest or the pressed hotkey
     * @param voids optional parameter
     */
    @Override
    protected Void doInBackground(Void... voids) {
        this.client = new Client();
        client.start();

        this.clientListener = new ClientListener();
        client.addListener(clientListener);

        registerKryoPackages(client);

        final InetAddress address = getConnectionAddress();
        connectToServer(address);

        return null;
    }

    /**
     * Command: Gets called after asynch task finishes
     */
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        endConnection();
    }

    /**
     * Command: Terminates client connection
     * sends user feedback if error occurs
     */
    private void endConnection(){
        if(!waitForResponse) {
            if(postTaskListener != null) postTaskListener.onPostTask(R.string.errorConnection);
            client.stop();
        }
    }

    /**
     * Command: Registers Kryo Packages for client
     * @param client connection client
     */
    private void registerKryoPackages(Client client){
        final Kryo kryo = client.getKryo();
        kryo.register(communicationObjects.Hotkey.class);
        kryo.register(communicationObjects.HotkeyMap.class);
        kryo.register(communicationObjects.MapRequest.class);
        kryo.register(java.util.HashMap.class);
    }

    /**
     * Command: Tries to connect to given server address
     * sends either a map request or a pressed hotkey
     * @param serverAddress server address
     */
    private void connectToServer(InetAddress serverAddress){
        try {
            if(serverAddress != null) {
                client.connect(5000, serverAddress, 9999, 54777);
                if (hotKey != null) {
                    client.sendTCP(hotKey);
                }
                else {
                    client.sendTCP(new MapRequest());
                    this.waitForResponse = true;
                    clientListener.setClient(client);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Command: Gets server address
     * submitted by user or by searching within the network
     * @return a valid server address
     */
    private InetAddress getConnectionAddress(){
        if(serverAddress != null && !serverAddress.isEmpty()){
            try {
                return InetAddress.getByName(serverAddress);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        } else {
            return client.discoverHost(54777, 5000);
        }
        return null;
    }

}
