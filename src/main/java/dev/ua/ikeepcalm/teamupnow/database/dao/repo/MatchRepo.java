package dev.ua.ikeepcalm.teamupnow.database.dao.repo;

import dev.ua.ikeepcalm.teamupnow.database.entities.Credentials;
import dev.ua.ikeepcalm.teamupnow.database.entities.Match;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepo extends CrudRepository<Match, Long> {
    Match findByFirstUserAndSecondUser(Credentials firstUser, Credentials secondUser);
    List<Match> findMatchesByFirstUserOrderByScore(Credentials firstUser);
    List<Match> findMatchesByFirstUser(Credentials firstUser);
    List<Match> findMatchesBySecondUser(Credentials secondUser);
    @Query("SELECT m FROM Match m WHERE m.firstUser = :user OR m.secondUser = :user")
    List<Match> findAllMatchesByUser(@Param("user") Credentials user);

}
