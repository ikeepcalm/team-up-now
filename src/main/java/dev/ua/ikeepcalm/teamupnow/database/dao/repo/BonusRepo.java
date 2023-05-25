package dev.ua.ikeepcalm.teamupnow.database.dao.repo;

import dev.ua.ikeepcalm.teamupnow.database.entities.Bonus;
import dev.ua.ikeepcalm.teamupnow.database.entities.Persistent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BonusRepo extends CrudRepository<Bonus, Long> {
    Optional<Bonus> findByCode(String code);
}
