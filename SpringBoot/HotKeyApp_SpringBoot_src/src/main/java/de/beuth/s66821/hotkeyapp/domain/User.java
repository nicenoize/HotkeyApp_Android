package de.beuth.s66821.hotkeyapp.domain;

/**
 * @author Alexander Stahl, Sebastian Voigt
 * @version 2017-10-30
 */

import javax.persistence.Entity;
import javax.persistence.Id;
import static multex.MultexUtil.create;
import java.util.UUID;
import javax.persistence.GeneratedValue;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.factory.annotation.Configurable;
import javax.persistence.Column;

@Entity
@Configurable
public class User{
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column (name = "id", updatable = false, nullable = false)
	private UUID id;
	
	@Column (name = "name", updatable = false, nullable = false)
    private String name;
	
    private String passwd;
	
    public User() {
    	this.name = null;
    	this.passwd = null;
    }
   /**
    * User constructor
    * @param name pseudo
    * @param passwd is the password for the user
    */
    public User(String name, String passwd){
   		if(name.isEmpty() || name.contains(" ") || name == null) throw create(User.UsernameMalformedExc.class);
       	if(passwd.length() < 8 || passwd == null) throw create(User.PasswdToShortExc.class);
       	
  		this.name = name.toLowerCase();
        this.passwd = passwd;
    }
	
    /** 
     * Get the users name
     * @return returns String the name of the user
     */
    public String getName() {return name;}
	
    /**
     * Get users password
     * @return the user given password
     */
    public String getPasswd() {return passwd;}
	
    /**
     * Sets a new password
     * @param passwd the new password
     */
    // TODO
    public void setPasswd(String passwd) {
    	if(passwd.length() < 8) throw create(User.PasswdToShortExc.class);
    	this.passwd = passwd;
    }
     
    /**
     * Get User-UUID
     * @return UUID the user uuid
     */
    public UUID getUserId() {return id;}   
    
    @Override
    public String toString() {
    	return String.format("User{id=%s, name='%s', passwd='%s'}", id, name, passwd);
    }
    
    ////////////////////////////////////
    // EXCEPTIONS 
    ////////////////////////////////////
    /**Username already in use: {0}*/
    @SuppressWarnings("serial")
	public static class UserExistsExc extends multex.Exc{}
    
    /**Password must have 8 or more chars.*/
    @SuppressWarnings("serial")
	public static class PasswdToShortExc extends multex.Exc{}    
    
    /**Username cant be null, empty nor contain whitespaces.*/
    @SuppressWarnings("serial")
	public static class UsernameMalformedExc extends multex.Exc{}	
    
    /**Username dosn´t exists: {0}*/
    @SuppressWarnings("serial")
	public static class UsernameDosentExistsExc extends multex.Exc{}
}