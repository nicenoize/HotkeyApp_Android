/**
 * @author Alexander Stahl
 * @version 2017-10-31
 * Required repository for {@link de.beuth.s66821.hotkeyapp.domain.HotkeyMap} objects.
 */
package de.beuth.s66821.hotkeyapp.domain.imports;

import java.util.List;
import java.util.Optional;
import de.beuth.s66821.hotkeyapp.domain.HotkeyMap;
import de.beuth.s66821.hotkeyapp.domain.User;

public interface HotkeyMapRepository{
    /**Finds all {@link HotkeyMap}s and returns them ordered by descending IDs.*/
    List<HotkeyMap> findAll();
      
	/**Returns the {@link HotkeyMap} object with the given id, if existing.
     * @throws IllegalArgumentException id is null*/
	Optional<HotkeyMap> findById(long id);
	
	/**Returns the {@link HotkeyMap} object with the given name, if existing.
     * @throws IllegalArgumentException name is null*/
	Optional<HotkeyMap> findByName(String progname);
	
    /**Save the {@link HotkeyMap}.
     * @return the modified instance */
    HotkeyMap save(HotkeyMap map);
    
    /**Vote work for a {@link HotkeyMap}.
     * @param id from the map
     * @param user 
     */
    void votedWorks(long id, User user);
    
    /**Vote wish for a {@link HotkeyMap}.
     * @param id from the map
     * @param user
     */
    void votedWish(long id, User user);
}