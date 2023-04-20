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
    List<Match> findMatchesByFirstUserOrderByScore(Credentials firstUser);
    List<Match> findMatchesByFirstUser(Credentials firstUser);
}
