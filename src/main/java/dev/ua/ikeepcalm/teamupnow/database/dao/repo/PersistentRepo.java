package dev.ua.ikeepcalm.teamupnow.database.dao.repo;

import dev.ua.ikeepcalm.teamupnow.database.entities.Persistent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersistentRepo extends CrudRepository<Persistent, Long> {
    Optional<Persistent> findById(Long id);
}
