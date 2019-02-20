package de.beuth.s66821.hotkeyapp.domain.imports;

/**
 * @author Alexander Stahl
 * @version 2017-10-31
 * Required repository for {@link de.beuth.s66821.hotkeyapp.domain.User} objects.
 */

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import de.beuth.s66821.hotkeyapp.domain.User;

public interface UserRepository {
	
    /**Deletes all Users. Useful for test scenarions in order to start with an empty User set.*/
    void deleteAll();

    /**Gives the User a unique, higher ID and saves the User.
     * @return the modified instance*/
    User save(User user);

    /**Deletes the given User.*/
    void delete(User user);

    /**Finds all {@link User}s and returns them ordered by descending IDs.*/
    List<User> findAll();

    /**Returns the {@link User} object with the given id, if existing.
     * @throws IllegalArgumentException  id is null
     */
    Optional<User> findByID(UUID uuid);
    
    /**Returns the {@link User} object with the given username, if existing.
     * @throws IllegalArgumentException username is null
     */
	Optional<User> findByName(String username);
}