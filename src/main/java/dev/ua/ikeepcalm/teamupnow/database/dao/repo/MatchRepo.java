package dev.ua.ikeepcalm.teamupnow.database.dao.repo;

import dev.ua.ikeepcalm.teamupnow.database.entities.Credentials;
import dev.ua.ikeepcalm.teamupnow.database.entities.Match;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatchRepo extends CrudRepository<Match, Long> {
    Optional<Match> findByFirstUserIdAndSecondUserId(Long firstUser, Long secondUser);
    @Query("SELECT e FROM Match e WHERE e.firstUserId = :id ORDER BY e.score DESC")
    List<Match> findByFirstUserAndDescScore(Long id);
}
