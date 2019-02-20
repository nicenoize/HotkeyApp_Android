package communicationObjects;

/**
 * HotKeyApp_GUI v0.5
 *
 * @author Alexander Stahl
 * @version 0.5
 */

public class Hotkey {
    /** Description of the operation.*/
    private String identifier;
    /** The combination of keypresses the executes this operation*/
    private String keyCombination;

    public Hotkey(){}

    /** Constuctor
     * @param identifier Name of Operation
     * @param keyCombination Combination of keys
     */
    public Hotkey(final String identifier, final String keyCombination){
        this.identifier = identifier;
        this.keyCombination = keyCombination;
    }

    /**
     * Command: Get the keyCombination
     * @return the keyCombination of this Hotkey
     */
    public String getKeyCombination() {
        return keyCombination;
    }

    /**
     * Command: Sets a keyCombination for this Hotkey
     * @param keyCombination the keyCombination
     */
    public void setKeyCombination(final String keyCombination) {
        this.keyCombination = keyCombination;
    }

    /**
     * Command: Get the Identifier for this Hotkey
     * @return the identifier of this hotkey
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Command: Sets a Identifier for this Hotkey
     * @param identifier the identifier
     */
    public void setIdentifier(final String identifier) {
        this.identifier = identifier;
    }
}