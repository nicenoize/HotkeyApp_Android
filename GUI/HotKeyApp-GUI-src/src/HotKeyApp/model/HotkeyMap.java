package HotKeyApp.model;

import java.util.HashMap;

/**
 * HotKeyApp_GUI v0.5
 * #############################
 * @author Alexander Stahl
 * @version 0.5
 * #############################
 * This class provides all saved Hotkeys for a program in a Map.
 */

 public class HotkeyMap{

    /** MAP-ID at REST-Service */
    private long id;

    /** Map author */
    private String author;

    /** All contributers of the Map */
    private String contributer;

    /** How many people have voted for this map */
    private long voteWish;

    /** How many people have voted for this map */
    private long voteWorks;

    /** Name of the program */
    private String programmname;

    /** This attribute goes true if 100 people vote that this map actually works  */
    private Boolean stable;

    /** Holds all Hotkeys for this program*/
    private HashMap<String, String> keys = new HashMap<>();

    /** Standard constructor */
    public HotkeyMap(){}

    /**
     * Constructor: Creates a new Instance of this class with a given program name
     * @param programname Name of the program
     */
    public HotkeyMap(final String programname){
        this.programmname = programname;
    }

    /**
     * Command: Adds a new Hotkey to this instance
     * @param hotkey The Hotkey to add
     */
    public String addKey (final Hotkey hotkey){
        final String identifier = hotkey.getIdentifier();
        final String keyCombination = hotkey.getKeyCombination();

        if(identifier == null || identifier.isEmpty()) return "Bitte geben Sie der Aktion einen Namen";
        if(keyCombination == null || keyCombination.isEmpty()) return "Keine leeren Befehle erlaubt";
        if(keyExists(hotkey)) return "Aktion: (" + identifier + ") bereits vorhanden!";
        if(combinationExists(hotkey)) return "Kombination: (" + keyCombination + ") bereits vorhanden!";
        if(!new Keypresses().isValidKeypress(hotkey)) return "Kombination enthält ungültige Keys";
        else{
             keys.put(identifier, keyCombination);
             return "Aktion (" + identifier + ") erfolgreich Hinuzugefügt";
        }
    }

    /**
     * Command: Get the value of a given Hotkey
     * @param hotkey The Hotkey
     * @return value (keyCombination) of this key
     */
    public String getValue(final Hotkey hotkey){
        if (keyExists(hotkey)) return keys.get(hotkey.getIdentifier());
        return "not found";
    }

    /**
     * Command: Tests if the given Hotkey allready exists in this HotkeyMap. Preventing from creating a Hotkey.identifier twice
     * for the same HotkeyMap.
     * @param hotkey The Hotkey to test
     * @return true if identifier allready exists in this HotkeyMap
     */
    public boolean keyExists(final Hotkey hotkey){
        if (keys.containsKey(hotkey.getIdentifier())) return true;
        return false;
    }

    /**
     * Command: Tests if the given Hotkey allready exists in this HotkeyMap. Preventing from creating a Hotkey.keyCombination
     * twice for the same HotkeyMap.
     * @param hotkey The Hotkey to test
     * @return true if keyCombination allready exists in this HotkeyMap
     */
    public boolean combinationExists(final Hotkey hotkey){
        if (keys.containsValue(hotkey.getKeyCombination())) return true;
        return false;
    }

    /**
     * Command: Get the id of this HotkeyMap
     * @return the id of this HotkeyMap
     */
    public HashMap<String, String> getAllKeys(){
        return this.keys;
    }

    /**
     * Command: Sets all keys of this HotkeyMap
     * @param keys the keys
     */
    public void setAllKeys(final HashMap<String, String> keys){
        this.keys = keys;
    }

    /**
     * Command: Get the id of this HotkeyMap
     * @return the id of this HotkeyMap
     */
    public long getId() {
        return id;
    }

    /**
     * Command: Sets the id of of this HotkeyMap
     * @param id the id
     */
    public void setId(final long id) {
        this.id = id;
    }

    /**
     * Command: Get the author of this HotkeyMap
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Command: Sets the author of this HotkeyMap
     * @param author name of the author
     */
    public void setAuthor(final String author) {
        this.author = author;
    }

    /**
     * Command: Get all contributer of this HotkeyMap
     * @return the contributer
     */
    public String getContributer() {
        return contributer;
    }

    /**
     * Command: Sets the names of all contributers of this HotkeyMap
     * @param contributer the names of the contributers
     */
    public void setContributer(final String contributer) {
        this.contributer = contributer;
    }

    /**
     * Command: Get number of votes (wish) for this HotkeyMap
     * @return number of votes
     */
    public long getVoteWish() {
        return voteWish;
    }

    /**
     * Command: Sets the number of total votes (wish) of this HotkeyMap
     * @param voteWish the number of votes
     */
    public void setVoteWish(final long voteWish) {
        this.voteWish = voteWish;
    }

    /**
     * Command: Get number of votes (works) for this HotkeyMap
     * @return number of votes
     */
    public long getVoteWorks() {
        return voteWorks;
    }

    /**
     * Command: Sets the number of total votes (works) of this HotkeyMap
     * @param voteWorks the number of votes
     */
    public void setVoteWorks(final long voteWorks) {
        this.voteWorks = voteWorks;
    }

    /**
     * Command: Get the programname of this HotkeyMap
     * @return the programname
     */
    public String getProgrammname() {
        return programmname;
    }

    /**
     * Command: Sets the programname of this HotkeyMap
     * @param programmname the programname
     */
    public void setProgrammname(final String programmname) {
        this.programmname = programmname;
    }

    /**
     * Command: Get the stable status of this HotkeyMap
     * @return status of this HotkeyMap
     */
    public Boolean getStable() {
        return stable;
    }

    /**
     * Command: Sets the status of this HotkeyMap
     * @param stable the status
     */
    public void setStable(final Boolean stable) {
        this.stable = stable;
    }
}