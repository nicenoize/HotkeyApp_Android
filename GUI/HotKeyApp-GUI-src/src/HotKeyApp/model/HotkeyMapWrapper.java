package HotKeyApp.model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * HotKeyApp_GUI v0.5
 * #############################
 * @author Alexander Stahl
 * @version 0.5
 * #############################
 * This is a WrappperClass to provide the user a sortable and filterable TableView of
 * available Maps.
 */
public class HotkeyMapWrapper extends RecursiveTreeObject<HotkeyMapWrapper> {

        /** Holds the status of this map.*/
        private StringProperty stable;
        /** Holds the name of the programm.*/
        private StringProperty programmname;
        /** Holds the map that has to wrapped by this class.*/
        private HotkeyMap map;

        /** Standard constructor */
        public HotkeyMapWrapper(){}

        /** Constuctor
         * @param map The map that needs wrapping.
         */
        public HotkeyMapWrapper(final HotkeyMap map){
            this.programmname = new SimpleStringProperty(map.getProgrammname());
            this.map = map;
            if(map.getStable() == true) this.stable = new SimpleStringProperty("Stable");
            else this.stable = new SimpleStringProperty("Experimental");
        }

    public HotkeyMap getMap() {
        return map;
    }

    public void setMap(final HotkeyMap map) {
        this.map = map;
    }

    public String getProgrammname() {
        return programmname.get();
    }

    public StringProperty programmnameProperty() {
        return programmname;
    }

    public void setProgrammname(final String programmname) {
        this.programmname.set(programmname);
    }

    public String getStable() {
        return stable.get();
    }

    public StringProperty stableProperty() {
        return stable;
    }

    public void setStable(String stable) {
        this.stable.set(stable);
    }
}
