package de.beuth.s66821.hotkeyapp.infrastructure;

/**
 * @author Alexander Stahl
 * @version 2017-10-31
 * Required repository for {@link de.beuth.s66821.hotkeyapp.domain.User} objects.
 */

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import de.beuth.s66821.hotkeyapp.domain.User;
import de.beuth.s66821.hotkeyapp.domain.imports.UserRepository;
import de.beuth.s66821.hotkeyapp.infrastructure.imports.ImportedUserJpaRepository;

@Service
public class UserJpaRepository implements UserRepository {
	private final ImportedUserJpaRepository impl;

    @Autowired
    public UserJpaRepository(final ImportedUserJpaRepository impl) {
        this.impl = impl;
    }
    
    public void delete(final User user) {
    	impl.delete(user);
    }

    public void deleteAll(){
    	impl.deleteAll();
    }

    public User save(final User user){
        return impl.save(user);
    }
        
    public List<User> findAll(){
        return impl.findAllByOrderByIdAsc();
    }

	public Optional<User> findByID(final UUID uuid) {
		return impl.findById(uuid);
	}
	
	public Optional<User> findByName(final String username) {
		return impl.findByName(username);
	}
}