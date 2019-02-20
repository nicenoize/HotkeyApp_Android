package de.beuth.s66821.hotkeyapp.rest_interface;
/**
 * @author Alexander Stahl
 * @version 2017-11-10
 * A Spring Web MVC controller offering a REST service for accessing all external operations of the application.
 */

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import de.beuth.s66821.hotkeyapp.domain.HotkeyMap;
import de.beuth.s66821.hotkeyapp.domain.HotkeyMapService;
import de.beuth.s66821.hotkeyapp.domain.User;

@Transactional
@RestController
public class ApplicationController {
	private final HotkeyMapService hotkeyMapService;
	
    @Autowired
    public ApplicationController(final HotkeyMapService hotkeyMapService) {
    	this.hotkeyMapService = hotkeyMapService;
	}
	////////////////////////////
	// REST-Service HotkeyMap
	////////////////////////////
    
	@GetMapping(path="/maps")
	public ResponseEntity<HotkeyMapResource[]> findHotkeyMaps(
		@RequestParam(name="", defaultValue="") final String progName){
    	System.out.printf("ApplicationController GET /maps/ name=%s\n",progName);
    	final List<HotkeyMap> maps = hotkeyMapService.findAllMaps();
    	return _mapsToResources(maps);
	}
	
    @PostMapping("/map/new")
    public ResponseEntity<HotkeyMapResource> createMap(@RequestBody  final HotkeyMapResource hotkeyMapResource) {
     	System.out.printf("ApplicationController POST /maps/new %s\n", hotkeyMapResource);
    	final HotkeyMap map = hotkeyMapService.createMap(hotkeyMapResource.destinationProgramName,hotkeyMapResource.hotkeys, hotkeyMapResource.author);
        return new ResponseEntity<>(new HotkeyMapResource(map), HttpStatus.CREATED);
    }
    
    @PostMapping("/map/{id}/vote/works")
    public ResponseEntity<HotkeyMapResource> votedWorks(@PathVariable final long id, @RequestBody final UserResource userResource) {
     	System.out.printf("ApplicationController POST /maps/vote/works %s\n", id);
    	final HotkeyMap map = hotkeyMapService.findMapById(id).get();
    	final Optional<User> user = hotkeyMapService.findUserByName(userResource.name);
    	if(user.isPresent()) {
    		map.setVotedWorks(user.get());
            return new ResponseEntity<>(HttpStatus.OK);
    	}
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
    
    @PostMapping("/map/{id}/vote/wish")
    public ResponseEntity<HotkeyMapResource> votedWish(@PathVariable final long id, @RequestBody final UserResource userResource) {
     	System.out.printf("ApplicationController POST /maps/vote/wish %s\n", id);
    	final HotkeyMap map = hotkeyMapService.findMapById(id).get();
    	final Optional<User> user = hotkeyMapService.findUserByName(userResource.name);
    	if(user.isPresent()) {
    		map.setVotedWish(user.get());
            return new ResponseEntity<>(HttpStatus.OK);
    	}
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PostMapping("/map/addKeys")
    public ResponseEntity<HotkeyMapResource> updateMap(@RequestBody  final HotkeyMapResource hotkeyMapResource) {
     	System.out.printf("ApplicationController POST /maps/new %s\n", hotkeyMapResource);
    	final HotkeyMap map = hotkeyMapService.updateMap(hotkeyMapResource.destinationProgramName,hotkeyMapResource.hotkeys, hotkeyMapResource.author);
        return new ResponseEntity<>(new HotkeyMapResource(map), HttpStatus.CREATED);
    }

    @GetMapping("/map/{id}")
    public ResponseEntity<HotkeyMap> findHotkeyMap(@PathVariable  final long id) {
    	System.out.printf("ApplicationController GET /map/%s\n", id);
    	final HotkeyMap map = hotkeyMapService.findMapById(id).get();
        return new ResponseEntity<>(map, HttpStatus.OK);
    }	
    
	@GetMapping(path="/map/name/{progname}")
	public ResponseEntity<HotkeyMap> findHotkeyMap(@PathVariable  final String progname){
		System.out.printf("ApplicationController GET /map/name/ name=%s\n",progname);
    	final HotkeyMap map = hotkeyMapService.findMapByName(progname).get();
    	return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	private ResponseEntity<HotkeyMapResource[]> _mapsToResources(final List<HotkeyMap> maps) {
		final Stream<HotkeyMapResource> result = maps.stream().map(c -> new HotkeyMapResource(c));
		final HotkeyMapResource[] resultArray = result.toArray(size -> new HotkeyMapResource[size]);
		return new ResponseEntity<>(resultArray, HttpStatus.OK);
	}
	/// REST-Service HotkeyMap ENDE ///
    
	
	////////////////////////////
	// REST-Service User
	////////////////////////////
    @PostMapping("/register")
    public ResponseEntity<UserResource> createUser(@RequestBody  final UserResource userResource) {
     	System.out.printf("ApplicationController POST /register %s\n", userResource);
    	final User user = hotkeyMapService.createUser(userResource.name,userResource.passwd);
        return new ResponseEntity<>(new UserResource(user), HttpStatus.CREATED);
    }
    
    @GetMapping(path="/users")
    public ResponseEntity<UserResource[]> findUsers(
    	@RequestParam(name="", defaultValue="") final String username) {
    	System.out.printf("ApplicationController GET /user/all name=%s\n",username);
    	final List<User> users = hotkeyMapService.findAllUsers();
    	return _usersToResources(users);
    }
    
    @GetMapping("/user/id/{userId}")
    public ResponseEntity<User> findUser(@PathVariable  final UUID userId) {
    	System.out.printf("ApplicationController GET /user/id/%s\n", userId);
    	final User user = hotkeyMapService.findUserByUUID(userId).get();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }	
    
    @GetMapping("/user/name/{username}")
    public ResponseEntity<User> findUser(@PathVariable  final String username) {
    	System.out.printf("ApplicationController GET /user/name/%s\n", username);
    	final User user = hotkeyMapService.findUserByName(username).get();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }	
    
    @PutMapping("/user/update/{username}")
    public ResponseEntity<User> updatePasswd(@RequestBody final UserResource userResource){
    	System.out.printf("ApplicationController PUT /user/id/%s\n", userResource);
		final User user = hotkeyMapService.findUserByName(userResource.name).get();
    	user.setPasswd(userResource.passwd);
    	final User _user = hotkeyMapService.updatePasswd(user);
		return new ResponseEntity<>(_user, HttpStatus.OK);
    }
    
    
    @DeleteMapping("/user/delete/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable  final String username) {
    	System.out.printf("ApplicationController DELETE /user/delete/%s\n", username);
    	final User user = hotkeyMapService.findUserByName(username).get();
    	hotkeyMapService.deleteUser(user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }	
        
	private ResponseEntity<UserResource[]> _usersToResources(final List<User> users) {
		final Stream<UserResource> result = users.stream().map(c -> new UserResource(c));
		final UserResource[] resultArray = result.toArray(size -> new UserResource[size]);
		return new ResponseEntity<>(resultArray, HttpStatus.OK);
	}
	
	/// REST-Service User ENDE ///
	
}