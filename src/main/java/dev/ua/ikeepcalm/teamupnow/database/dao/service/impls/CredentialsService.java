package dev.ua.ikeepcalm.teamupnow.database.dao.service.impls;

import dev.ua.ikeepcalm.teamupnow.database.dao.repo.CredentialsRepo;
import dev.ua.ikeepcalm.teamupnow.database.dao.repo.GameRepo;
import dev.ua.ikeepcalm.teamupnow.database.dao.service.iCredentials;
import dev.ua.ikeepcalm.teamupnow.database.entities.Credentials;
import dev.ua.ikeepcalm.teamupnow.database.exceptions.DAOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CredentialsService implements iCredentials<Credentials> {

    private final CredentialsRepo credentialsRepo;
    private final GameRepo gameRepo;

    @Autowired
    public CredentialsService(CredentialsRepo credentialsRepo, GameRepo gameRepo) {
        this.credentialsRepo = credentialsRepo;
        this.gameRepo = gameRepo;
    }

    @Override
    public Credentials findByAccountId(long accountId) {
        Optional<Credentials> credentials = credentialsRepo.findCredentialsByAccountId(accountId);
        if (credentials.isPresent()) {
            return credentials.get();
        } else {
            throw new DAOException("Couldn't find Credentials object by accountID: " + accountId);
        }
    }

    @Override
    public void deleteCredentials(long accountId) {
        credentialsRepo.delete(findByAccountId(accountId));
    }

    @Override
    public List<Credentials> findAll() {
        return (List<Credentials>) credentialsRepo.findAll();
    }

    @Override
    public void saveAll(List<Credentials> list) {
        credentialsRepo.saveAll(list);
    }

    @Override
    public List<Credentials> findAllExcept(Long excludedId) {
        return credentialsRepo.findAllExcept(excludedId);
    }

    @Override
    public void save(Credentials credentials) {
        credentialsRepo.save(credentials);
    }

    @Override
    public void deleteGamesLinkedToCredentials(Credentials credentials){
        gameRepo.deleteGamesByCredentials(credentials);
    }
}
