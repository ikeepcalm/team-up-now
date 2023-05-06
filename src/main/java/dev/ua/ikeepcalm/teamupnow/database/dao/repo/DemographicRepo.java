package dev.ua.ikeepcalm.teamupnow.database.dao.repo;

import dev.ua.ikeepcalm.teamupnow.database.entities.Demographic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DemographicRepo extends CrudRepository<Demographic, Long> {
    Optional<Demographic> findDemographicByCredentialsId(Long credentialsId);
}
