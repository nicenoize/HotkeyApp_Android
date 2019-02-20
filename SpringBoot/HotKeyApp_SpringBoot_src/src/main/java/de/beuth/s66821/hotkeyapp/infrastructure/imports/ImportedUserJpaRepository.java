package de.beuth.s66821.hotkeyapp.infrastructure.imports;

/**Required repository for {@link de.beuth.s66821.hotkeyapp.domain.User} objects. The methods are named according to the Spring Data JPA convention.
 * They can be implemented by Spring during bean creation, but can be implemented independently of Spring, too.
 * @author Alexander Stahl
 * @version 2017-11-10
 */

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import de.beuth.s66821.hotkeyapp.domain.User;

public interface ImportedUserJpaRepository extends JpaRepository<User, UUID>{
    void deleteAll();

    @SuppressWarnings("unchecked")
	User save(User user);
    
    void delete(User user);
    
    // Find all Users
    List<User> findAllByOrderByIdAsc();
    
    // Find User by ID
    Optional<User> findById(UUID id);
    
    // Find User by Name
    Optional<User> findByName(String username);
   
}
