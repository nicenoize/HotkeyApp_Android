package de.beuth.s66821.hotkeyapp;

import static org.junit.Assert.fail;

import java.util.Random;

/**
 * @author Sebastian Voigt
 */


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import de.beuth.s66821.hotkeyapp.domain.HotkeyMap;
import de.beuth.s66821.hotkeyapp.domain.HotkeyMapService;
import de.beuth.s66821.hotkeyapp.domain.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HotKeyMapTest {

	@Autowired
	private HotkeyMapService hotkeyMapService;
	
	////////////////////////////////////
	// Random-String-Generate
	////////////////////////////////////
	public static String generateRandomString(int length)
	{
		if(length < 0) length = -length;
		Random rng = new Random();
		final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_.:;#~{[]}+-*/!§$%&()=?<>|\"";
	    char[] text = new char[length];
	    for (int i = 0; i < length; i++)
	    {
	        text[i] = characters.charAt(rng.nextInt(characters.length()));
	    }
	    return new String(text);
	}
	
	////////////////////////////////////
	// Don't allow hotkeyMap with same name as existing
	////////////////////////////////////
	@Test
	public void generateHotKeyMapsTest() {
		final int mapsToTest = 100;
		
		final User user = hotkeyMapService.createUser("Test1", "12345678");
        for(int i = 0; i < mapsToTest; i++) {
        	String mapName = generateRandomString(6);

        	hotkeyMapService.createMap(mapName, null, user);
        }

		hotkeyMapService.createMap("Test1", null, user);
		
		try {
			hotkeyMapService.createMap("Test1", null, user);
			fail("MapAlreadyExistsExc excepted");
		} catch (HotkeyMap.MapAlreadyExistsExc e) {
		}
	}

	////////////////////////////////////
	// 1 User can only vote once for a working hotkeyMap
	////////////////////////////////////
	@Test
	public void oneWorkVoteForUserPerHotkeymapTest() {
		
		User user = hotkeyMapService.createUser("test3", "12345678");
		User user_second = hotkeyMapService.createUser("test3.1", "12345678");
		final HotkeyMap hotkeymapTestWork = new HotkeyMap("testmap3", null, user);
		
		hotkeymapTestWork.setVotedWorks(user_second);
		
		try {
			hotkeymapTestWork.setVotedWorks(user_second);
			fail("AlreadyVotedExc excepted");
		} catch (HotkeyMap.AlreadyVotedExc e) {
		}
	}

	////////////////////////////////////
	// 1 User can only wish once for a hotkeyMap
	////////////////////////////////////
	@Test
	public void oneWishVoteForUserPerHotkeymapTest() {
		
		final User user = hotkeyMapService.createUser("Test5", "12345678");
		final User second_user = hotkeyMapService.createUser("Test5.1", "12345678");
		final HotkeyMap hotkeymapTestWish = new HotkeyMap("Testmap5", null, user);

		hotkeymapTestWish.setVotedWish(second_user);

		try {
			hotkeymapTestWish.setVotedWish(second_user);
			fail("AlreadyVotedExc excepted");
		} catch (HotkeyMap.AlreadyVotedExc e) {
		}
	}
}
