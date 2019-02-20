package HotKeyApp.model;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import HotKeyApp.model.Hotkey;
import HotKeyApp.model.HotkeyMap;
import HotKeyApp.model.Keypresses;
import HotKeyApp.model.MapRequest;

/**
 * HotKeyApp_GUI v0.5
 *
 * @author Alexander Stahl
 * @version 0.5
 */
public class HotkeyServerListener extends Listener {

    /** The user selected Map*/
    private HotkeyMap map;

    /**
     * Constructor: Creates the Listener with the user selected HotkeyMap
     * @param map map of selected program
     */
    public HotkeyServerListener(HotkeyMap map){
        this.map = map;
    }

    /**
     * Client has established a connection
     * @param connection the established connection
     */
    @Override
    public void connected(Connection connection){}

    /**
     * Client has disconnected
     * @param connection the connection that has closed
     */
    @Override
    public void disconnected(Connection connection){}

    /**
     * Listens for a incomming message from the client.
     * @param connection the connetion that has send a object to this server
     * @param object the object itself
     */
    @Override
    public void received(Connection connection, Object object){
        System.out.println("Objekt erhalten: " + object);
        if(object instanceof Hotkey){
            new Keypresses().executeKeys((Hotkey) object);
        }

        else if(object instanceof MapRequest){
            connection.sendTCP(map);
        }
    }
}