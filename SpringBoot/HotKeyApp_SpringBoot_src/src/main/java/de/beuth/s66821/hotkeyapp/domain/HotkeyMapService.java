package de.beuth.s66821.hotkeyapp.domain;

import java.util.HashMap;

/**
 * @author Alexander Stahl
 * @version 2017-11-10
 * This is a domain service for User and HotkeyMap.
 */

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static multex.MultexUtil.create;
import de.beuth.s66821.hotkeyapp.domain.imports.HotkeyMapRepository;
import de.beuth.s66821.hotkeyapp.domain.imports.UserRepository;


@Service
public class HotkeyMapService {
	
	// Required repositories 
	private final UserRepository userRepository;
	private final HotkeyMapRepository mapRepository;
	
	@Autowired
	public HotkeyMapService(final UserRepository userRepository, final HotkeyMapRepository mapRepository) {
		this.userRepository = userRepository;
		this.mapRepository = mapRepository;
	}
	
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // USER
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**Command: Creates a user with the given name, password and generate a random UUID.
	* @param name the name of the new User, must not be empty and do not have white spaces 
	* @param passwd the password of the new user, must have at least 8 chars
	* @return the created User 
	*/
	public User createUser(final String name, final String passwd) {
		if(name == null) {
			return new User("",passwd);
		}
		final boolean exsist = userRepository.findByName(name.toLowerCase()).isPresent();
		if(exsist) 
			throw create(User.UserExistsExc.class, name);
		final User user = userRepository.save(new User(name, passwd));
	    return user;
	}
	
	// TODO prüfen ob user existiert und old pass stimmt
	public User updatePasswd(final User user) {
		final User _user = userRepository.save(user);
		return _user;
	}
	
	/**Command: Find a user by UUID.
	* @param uuid finds a user by uuid, can´t be null nor null
	* @return the uuid from the User*/
    public Optional<User> findUserByUUID(final UUID uuid) {
        return userRepository.findByID(uuid);
    }
    
	
	/**Command: Find user by name.
	* @param name can´t be null nor empty
	* @return the User with the name of the User*/
    public Optional<User> findUserByName(final String name) {
        return userRepository.findByName(name);
    }
    
    /**Query: Finds all user
     * @return all User*/
    public List<User> findAllUsers(){
        return userRepository.findAll();
    }
    
    /**Command: Deletes the given {@link User}.
     * @param user will be delete, can't be NULL*/
    public void deleteUser(final User user){
        userRepository.delete(user);
    }
    
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // MAPS
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**Command: Creates a HotkeyMap with program name, keys and author.
	* @param progname is the name of the program, must not be empty
	* @param keys are the shortcut of the map
	* @param author is the user who created the map 
	* @return a new map
	*/
	public HotkeyMap createMap(final String progname, final HashMap<String, String> keys, final User author) {
		final Optional<User> user = userRepository.findByName(author.getName().toLowerCase());
		final Optional<HotkeyMap> map = mapRepository.findByName(progname);
		if(!user.isPresent()) throw create(User.UsernameDosentExistsExc.class, author.getName()); 
		if(map.isPresent()) throw create(HotkeyMap.MapAlreadyExistsExc.class, progname);
		final HotkeyMap mapToSave = mapRepository.save(new HotkeyMap(progname,keys,user.get()));
	    return mapToSave;
	}
	
	/**
	 * Command: Updates the keys of a {@link HotkeyMap} and saves the added {@link Hotkey} to this map.
	 * @param progname name of the program
	 * @param keys contains all hotkeys
	 * @param contributer is the user who updated the map
	 * @return update map
	 */
	public HotkeyMap updateMap(final String progname, final HashMap<String, String> keys, final User contributer) {
		final Optional<User> user = userRepository.findByName(contributer.getName().toLowerCase());
		final Optional<HotkeyMap> map = mapRepository.findByName(progname);
		if(!user.isPresent()) throw create(User.UsernameDosentExistsExc.class, contributer.getName()); 
		if(!map.isPresent()) throw create(HotkeyMap.MapDosntExistExc.class, progname);
		map.get().addHotkeys(keys);
		map.get().setContributer(user.get());
		final HotkeyMap mapToSave = mapRepository.save(map.get());
	    return mapToSave;
	}
    
    /**
	 * Command: Find a map by name.
	 * @param progname find the map by name
	 * @return the map by programName
	 */
	public Optional<HotkeyMap> findMapByName(final String progname){
		return mapRepository.findByName(progname);
	}
	
	/**
	 * Command: Find a map by id.
	 * @param id find the map by id
	 * @return the map by id
	 */
	public Optional<HotkeyMap> findMapById(final long id){
		return mapRepository.findById(id);
	}
	
	/**
	 * Query: Finds all maps
	 * @return all maps
	 */
    public List<HotkeyMap> findAllMaps(){
        return mapRepository.findAll();
    }
 }