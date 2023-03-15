package dev.ua.ikeepcalm.teamupnow.database.dao.service.impls;

import dev.ua.ikeepcalm.teamupnow.database.dao.repo.CredentialsRepo;
import dev.ua.ikeepcalm.teamupnow.database.dao.repo.GameRepo;
import dev.ua.ikeepcalm.teamupnow.database.dao.service.DatabaseService;
import dev.ua.ikeepcalm.teamupnow.database.entities.Credentials;
import dev.ua.ikeepcalm.teamupnow.database.entities.Game;
import dev.ua.ikeepcalm.teamupnow.database.exceptions.DAOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameService implements DatabaseService<List<Game>> {

    @Autowired
    GameRepo gameRepo;

    @Autowired
    CredentialsService credentialsService;

    @Override
    public List<Game> findByAccountId(long accountId) {
        Credentials credentials = credentialsService.findByAccountId(accountId);
        List<Game> gameList = gameRepo.findGamesByCredentialsId(credentials.getId());
        return gameList;
    }
}
