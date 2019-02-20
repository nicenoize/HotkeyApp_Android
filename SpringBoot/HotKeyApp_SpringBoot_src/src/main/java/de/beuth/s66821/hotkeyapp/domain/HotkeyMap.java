package de.beuth.s66821.hotkeyapp.domain;

import static multex.MultexUtil.create;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.ManyToMany;
import org.springframework.beans.factory.annotation.Configurable;

/**
 * @author Alexander Stahl, Sebastian Voigt
 * @version 2017-10-31
 * 
 * A HotkeyMap for multiple Hotkeys for a program.
 */

@Entity
@Configurable
public class HotkeyMap {
		
		@Id 
		@GeneratedValue
	    private long id;
	    private String destinationProgramName;
		@Column (name = "keys", nullable = true, length=8192, updatable = true)
	    private HashMap<String, String> hotkeys = new HashMap<>();
		@ManyToOne
		private User author;
	    @ManyToMany
	    private List<User> votedWorks = new ArrayList<>();
	    @ManyToMany
	    private List<User> votedWish = new ArrayList<>();
	    @ManyToMany
	    private List<User> contributer = new ArrayList<>();
	    private boolean stable = false;
	    
	    /**
	     * Constructor for HotkeyMap
	     * @param progname is the name of the program
	     * @param keys are the shortcut keys of that program
	     * @param user is the author of the hotkeymap 
	     */
	    public HotkeyMap (final String progname, final HashMap<String, String> keys, final User user) {
	    	this.destinationProgramName = progname;
	    	this.hotkeys = keys;
	    	this.author = user;
	    	contributer.add(user);
	    	votedWish.add(user);
	    	votedWorks.add(user);
	    } 

	    /** Default Constructor */
	    @SuppressWarnings("unused")
		private HotkeyMap () {}
	    
	    /**
	     * Get the id of the program
	     * @return the id of the program
	     */
	    public long getID() 					{ return this.id; }

	    /**
	     * Get the name of the program
	     * @return the name of the program
	     */	    
	    public String getProgramName() 			{ return this.destinationProgramName; }	    
	    
	    /**
	     * Get the hashmap with the keys
	     * @return hashmap with the keys
	     */
	    public HashMap<String, String> getMap() { return this.hotkeys; }
	    
	    /**
	     * Get the author of the map
	     * @return the author of the map
	     */
	    public User getAuthor()					{ return this.author; }
	    
	    /**
	     * Get the boolean if stable or not
	     * @return a boolean if the map is stable or not
	     */
	    public boolean getStable()				{ return this.stable;}

	    /**
	     * 
	     * @param hotkey create a new Hotkey
	     */
	    public void createHotkey(Hotkey hotkey) {
	    	hotkeys.put(hotkey.getIdentifier(), hotkey.getKeyCombination());
	    }
	    
	    /**
	     * Getter for hotKeys with identifier
	     * @param identifier get the Hotkey by identifier
	     * @return Hotkey
	     */	    
	    public String getHotkeyValue(final String identifier) {
	    	if (hotkeys.containsKey(identifier)) return hotkeys.get(identifier);
	    	return null;
	    }
	    
	    /**
	     * Setter for contributer
	     * @param user help to create this map (with this he will be add as a contributer)
	     */	    
	    public void setContributer(final User user) {
	    	if(!contributer.contains(user)) this.contributer.add(user);
	    }
	    
	    /**
	     * Getter for contributer
	     * @return contributer
	     */	    
	    public List<User> getContributer(){
	    	return this.contributer;
	    }
	    
	    /**
	     * Setter for voteWish
	     * @param user vote for one map he would like to have
	     */	    
	    public void setVotedWish(final User user) {
	    	if(votedWish.contains(user)) throw create(HotkeyMap.AlreadyVotedExc.class, user);
	    	else this.votedWish.add(user);
	    }
	    
	    /**
	     * Getter for voteWish
	     * @return voteWish
	     */	    
	    public int getVotedWish() {
	    	return votedWish.size();
	    }
	    
	    /**
	     * Setter for voteWork
	     * @param user vote for one map that work fine
	     */	    
	    public void setVotedWorks(final User user) {
	    	if(votedWorks.contains(user)) throw create(HotkeyMap.AlreadyVotedExc.class, user);
	    	else this.votedWorks.add(user);
	    	if(getVotedWorks() == 100) this.stable = true;
	    }
	    
	    /**
	     * Getter for voteWork
	     * @return voteWork
	     */	    
	    public int getVotedWorks() {
	    	return votedWorks.size();
	    }
	    
	    /**
	     * Adding new Hotkeys to this map
	     * @param newKeys the collection of keys
	     */
	    public void addHotkeys(final HashMap<String, String> newKeys) {
	    	for (String ident : newKeys.keySet()) {
	    		final Hotkey hotkey = new Hotkey(ident, newKeys.get(ident));
	    		
	    		if(!identifierExists(hotkey) && !keyCombinationExists(hotkey)) {
	    			if(hotkey.isValidKeypress(hotkey)) {
	    				this.hotkeys.put(hotkey.getIdentifier(), hotkey.getKeyCombination().toLowerCase());
	    			}
	    		}
	    	}
	    }
	    
	    public boolean identifierExists(Hotkey hotkey) {
	    	return hotkeys.containsKey(hotkey.getIdentifier());
	    }
	    
	    public boolean keyCombinationExists(Hotkey hotkey) {
	    	return hotkeys.containsValue(hotkey.getKeyCombination());
	    }
	    
		////////////////////////////////////
		// EXCEPTIONS 
		////////////////////////////////////
		/**You have already voted for this Map: {0}*/
		@SuppressWarnings("serial")
		public static class AlreadyVotedExc extends multex.Exc{}
		
		/**This Map already contains the key: {0}*/
		@SuppressWarnings("serial")
		public static class StableMapsAreNotEditableExc extends multex.Exc{}
		
		/**A Map with the name: {0} already exists*/
		@SuppressWarnings("serial")
		public static class MapAlreadyExistsExc extends multex.Exc{}
		
		/**A Map with the name: {0} dosn´t exists*/
		@SuppressWarnings("serial")
		public static class MapDosntExistExc extends multex.Exc{}
		
		
}