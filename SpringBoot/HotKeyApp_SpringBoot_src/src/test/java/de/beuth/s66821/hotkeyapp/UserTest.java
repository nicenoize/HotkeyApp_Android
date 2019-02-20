package de.beuth.s66821.hotkeyapp;

/**
 * @author Alexander Stahl
 * @version 2017-11-10
 */

import static org.junit.Assert.*;
import java.util.Random;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import de.beuth.s66821.hotkeyapp.domain.HotkeyMapService;
import de.beuth.s66821.hotkeyapp.domain.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {
	
	@Autowired
	private HotkeyMapService hotkeyMapService;
	
    ////////////////////////////////////
    // Generates x Users and tests
    // UUIDs against each other
    ////////////////////////////////////
    @Test
    public void userUUID() {
    	final int usersToTest = 100;
        final String[] usernames = new String[usersToTest];

        // Generate users
        for(int i = 0; i < usersToTest; i++) {
        	final String username = generateRandomString(6);
        	final String passwd = generateRandomString(8);
        	
            usernames[i] = username.toLowerCase();
        	hotkeyMapService.createUser(username, passwd);
        }

        // Test UUIDs
        for(int i = 0; i < usernames.length-2; i++){
            for(int j = usernames.length-1; j > i; j--){
                final User user1 = hotkeyMapService.findUserByName(usernames[i]).get();
                final User user2 = hotkeyMapService.findUserByName(usernames[j]).get();
                assertTrue("uuid: " + user1.getUserId() + ", uuid2: " + user2.getUserId(), user1.getUserId() != user2.getUserId());
            }
        }
    }
    
    ////////////////////////////////////
    // Duplicates for Username are
    // disallowed
    ////////////////////////////////////
    @Test
    public void userNameDuplicates() {
    	hotkeyMapService.createUser("TOTTI", generateRandomString(12));
    	
    	final User[] sameNames = {
        		new User("tOTTI", generateRandomString(12)),
        		new User("ToTTI", generateRandomString(12)),
        		new User("TOtTI", generateRandomString(12)),
        		new User("TOTtI", generateRandomString(12)),
        		new User("TOTTi", generateRandomString(12)),
        		new User("toTTI", generateRandomString(12)),
        		new User("totTI", generateRandomString(12)),
        		new User("tottI", generateRandomString(12)),
        		new User("totti", generateRandomString(12)),
        		new User("TottI", generateRandomString(12)),
        		new User("ToTtI", generateRandomString(12))
    	};
    	
    	for(User u : sameNames) {
        	try {
        		hotkeyMapService.createUser(u.getName(), u.getPasswd());
        		fail("User.UserExistsExc expected");
        	}
        	catch (User.UserExistsExc expected) {}
    	}
    }
    
    ////////////////////////////////////
    // Passwords need min lenght
    // of 8 chars
    ////////////////////////////////////
    @Test
    public void userPasswdLength() {
        final int testcases = 1000;
        final User user = hotkeyMapService.createUser("TestUser", "01234567");        
        
        // testcases that have to fail, because passwd is too short. Throws Exception!
        for (int i = 0; i < testcases; i++) {
        	
        	// Generate new Password
        	try {
        		user.setPasswd(generateRandomString(6));
      			fail("User.PasswdToShortExc excepted");
        	}
        	catch(User.PasswdToShortExc e){}
        }
        
        // testcases that succeed, no exception is thrown.
        for (int i = 0; i < testcases; i++) {
        	
        	// Generate new Password
        	user.setPasswd(generateRandomString(8));
        }
    }
    
    ////////////////////////////////////
    // Generates Random Strings with
    // provided length
    ////////////////////////////////////
	public static String generateRandomString(int length)
	{
		if(length < 0) length = -length;
		final Random rng = new Random();
		final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_.:;#~{[]}+-*/!§$%&()=?<>|\"";
	    final char[] text = new char[length];
	    for (int i = 0; i < length; i++)
	    {
	        text[i] = characters.charAt(rng.nextInt(characters.length()));
	    }
	    return new String(text);
	}
	
    ////////////////////////////////////
    // Username can't be NULL
    ////////////////////////////////////
    @Test
    public void malformedUserName() {
    	
        try {
        	hotkeyMapService.createUser(null, generateRandomString(12));
        	fail("User.UsernameMalformedExc expected");
        	hotkeyMapService.createUser("", generateRandomString(12));
        	fail("User.UsernameMalformedExc expected");
        }
        catch (User.UsernameMalformedExc expected) {}
    }
}
