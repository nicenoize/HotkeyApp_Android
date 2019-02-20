package de.beuth.s66821.hotkeyapp.rest_interface;

import de.beuth.s66821.hotkeyapp.domain.Hotkey;
/**
 * @author Alexander Sthal, Sebastian voigt
 */
public class HotkeyResource {

	/**Identifier for the keyCombination*/
	public String identifier;

	/**keyCombination like Strg+C*/
	public String keyCombination;
	
	/**Necessary for Jackson*/
	public HotkeyResource() {}
	
	/**Constructs a HotkeyResource with the data of the passed Hotkey entity.*/
	public HotkeyResource(final Hotkey entity) {
		this.identifier = entity.getIdentifier();
		this.keyCombination = entity.getKeyCombination();
	}
	/** ToString method*/
	public String toString() {
		return "Identifier: " + this.identifier + ", Hotkey: " + this.keyCombination;
	}
}