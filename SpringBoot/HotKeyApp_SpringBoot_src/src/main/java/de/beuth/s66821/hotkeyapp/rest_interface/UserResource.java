package de.beuth.s66821.hotkeyapp.rest_interface;

/**
 * @author Alexander Stahl, Sebastian Voigt
 */

import java.util.UUID;
import de.beuth.s66821.hotkeyapp.domain.User;

public class UserResource {

    /**Unique ID of the user.*/
	public UUID id;
	
    /**Complete name of the user.*/
    public String name;
	
    /**The users password*/
    public String passwd;

    /**Necessary for Jackson*/
	public UserResource() {}

    /**Constructs a ClientResource with the data of the passed Client entity.*/
    public UserResource(final User entity) {
    	this.id = entity.getUserId();
        this.name = entity.getName();
        this.passwd = entity.getPasswd();
    }

    @Override
    public String toString() {
    	return String.format("User{id=%s, name='%s', passwd='%s'}", id, name, passwd);
    }
}

