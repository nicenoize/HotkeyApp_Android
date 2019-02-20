package de.beuth.s66821.hotkeyapp.infrastructure.imports;

import java.util.List;
import java.util.Optional;

/**Required repository for {@link de.beuth.s66821.hotkeyapp.domain.HotkeyMap} objects. The methods are named according to the Spring Data JPA convention.
 * They can be implemented by Spring during bean creation, but can be implemented independently of Spring, too.
 * @author Alexander Stahl*/

import org.springframework.data.jpa.repository.JpaRepository;
import de.beuth.s66821.hotkeyapp.domain.HotkeyMap;
import de.beuth.s66821.hotkeyapp.domain.User;

public interface ImportedHotkeyMapJpaRepository extends JpaRepository<HotkeyMap, Long> {

	List<HotkeyMap> findAllByOrderByIdAsc();
    
	Optional<HotkeyMap> findById(long id);
	
	Optional<HotkeyMap> findByDestinationProgramName(String progname);

    @SuppressWarnings("unchecked")
	HotkeyMap save(HotkeyMap map);
    
    void votedWorks(long id, User user);
    
    void votedWish(long id, User user);
    
}
	
