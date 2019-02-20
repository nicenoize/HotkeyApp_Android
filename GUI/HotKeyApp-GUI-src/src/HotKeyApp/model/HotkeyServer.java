package HotKeyApp.model;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;

/**
 * HotKeyApp_GUI v0.5
 * #############################
 * @author Alexander Stahl
 * @version 0.5
 * #############################
 * This is a WrappperClass to provide the user a sortable and filterable TableView of
 * available Maps.
 */
public class HotkeyServer {

    /** The Server */
    private Server server;

    /** Creates an instance of a Server */
    public HotkeyServer(){
        this.server = new Server();
    }

    /** Starts this instance */
    public void start(HotkeyMap map){
        // Für den Listener
        server.start();
        System.out.println("[Server] Server gestarted");

        // ServerMain an Ports binden(9999 = TCP, 54777 = UDP)
        try {
            server.bind(9999,54777);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Listener für die communicationObjects anmelden
        server.addListener(new HotkeyServerListener(map));

        // Pakete (communicationObjects) anmelden
        Kryo kryo = server.getKryo();
        kryo.register(Hotkey.class);
        kryo.register(HotkeyMap.class);
        kryo.register(MapRequest.class);
        kryo.register(java.util.HashMap.class);
    }

    /** Stops this instance */
    public void stop(){
        server.stop();
        server.close();
        System.out.println("[Server] Server gestoppt!");
    }

    ///////////////////////////
    // EXCEPTIONS
    ///////////////////////////
    //TODO
}
