package de.beuth.s66821.hotkeyapp.rest_interface;

import java.util.HashMap;
import java.util.List;
import de.beuth.s66821.hotkeyapp.domain.HotkeyMap;
import de.beuth.s66821.hotkeyapp.domain.User;

/**Data about a HotkeyMap. Usable as Data Transfer Object.*/
public class HotkeyMapResource {
    public long id;
    public String destinationProgramName;
    public HashMap<String,String> hotkeys;
    public User author;
    public int votedWorks;
    public int votedWish;
    public List<User> contributer;
    public boolean stable;
    
    /**Necessary for Jackson*/
    public HotkeyMapResource() {}
    
    public HotkeyMapResource (final HotkeyMap entity) {
    	this.id = entity.getID();
    	this.destinationProgramName = entity.getProgramName();
    	this.hotkeys = entity.getMap();
    	this.author = entity.getAuthor();
    	this.stable = entity.getStable();
    	this.votedWish = entity.getVotedWish();
    	this.votedWorks = entity.getVotedWorks();
    	this.contributer = entity.getContributer();
    }
    
    @Override
    public String toString() {
    	return String.format("Map{id=%s, destinationProgName='%s', hotkeys='%s'}", id, destinationProgramName, hotkeys);

    }
}
