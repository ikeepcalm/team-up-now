package dev.ua.ikeepcalm.teamupnow.database.dao.repo;

import dev.ua.ikeepcalm.teamupnow.database.entities.Credentials;
import dev.ua.ikeepcalm.teamupnow.database.entities.Game;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepo extends CrudRepository<Game, Long> {
    List<Game> findGamesByCredentialsId(Long credentialsId);
    void deleteGamesByCredentials(Credentials credentialsId);
}
