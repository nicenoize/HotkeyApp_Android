package de.beuth.s66821.hotkeyapp.infrastructure;

/**
 * @author Alexander Stahl
 * @version 2017-10-31
 * Required repository for {@link de.beuth.s66821.hotkeyapp.domain.HotkeyMap} objects.
 */

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.beuth.s66821.hotkeyapp.domain.HotkeyMap;
import de.beuth.s66821.hotkeyapp.domain.User;
import de.beuth.s66821.hotkeyapp.domain.imports.HotkeyMapRepository;
import de.beuth.s66821.hotkeyapp.infrastructure.imports.ImportedHotkeyMapJpaRepository;

@Service
public class HotkeyMapJpaRepository implements HotkeyMapRepository{

	private final ImportedHotkeyMapJpaRepository impl;
	
    @Autowired
    public HotkeyMapJpaRepository(final ImportedHotkeyMapJpaRepository impl) {
        this.impl = impl;
    }
    
	public List<HotkeyMap> findAll() {
		return impl.findAllByOrderByIdAsc();
	}

	public Optional<HotkeyMap> findById(final long id) {
		return impl.findById(id);
	}
	
	public Optional<HotkeyMap> findByName(final String progname) {
		return impl.findByDestinationProgramName(progname);
	}
	
    public HotkeyMap save(final HotkeyMap map) {
    	return impl.save(map);
    }
    
    public void votedWorks(final long id, final User user) {
    	impl.votedWorks(id, user);
    }
    
    public void votedWish(final long id, final User user) {
    	impl.votedWorks(id, user);
    }
}