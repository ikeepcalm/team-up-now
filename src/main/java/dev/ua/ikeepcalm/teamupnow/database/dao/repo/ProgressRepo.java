package dev.ua.ikeepcalm.teamupnow.database.dao.repo;

import dev.ua.ikeepcalm.teamupnow.database.entities.Progress;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProgressRepo extends CrudRepository<Progress, Long> {
    public Optional<Progress> findProgressByCredentialsId(Long credentialsId);
}
