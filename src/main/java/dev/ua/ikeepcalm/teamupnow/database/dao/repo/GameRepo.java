package dev.ua.ikeepcalm.teamupnow.database.dao.repo;

import dev.ua.ikeepcalm.teamupnow.database.entities.Game;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepo extends CrudRepository<Game, Long> {
    public List<Optional<Game>> getGamesByCredentialsId(Long credentialsId);
}
