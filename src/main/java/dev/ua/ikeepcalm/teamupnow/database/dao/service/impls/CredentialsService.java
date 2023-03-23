package dev.ua.ikeepcalm.teamupnow.database.dao.service.impls;

import dev.ua.ikeepcalm.teamupnow.database.dao.repo.CredentialsRepo;
import dev.ua.ikeepcalm.teamupnow.database.dao.service.DatabaseService;
import dev.ua.ikeepcalm.teamupnow.database.entities.Credentials;
import dev.ua.ikeepcalm.teamupnow.database.entities.Game;
import dev.ua.ikeepcalm.teamupnow.database.exceptions.DAOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CredentialsService implements DatabaseService<Credentials> {

    @Autowired
    CredentialsRepo credentialsRepo;

    @Override
    public Credentials findByAccountId(long accountId) {
        Optional<Credentials> credentials = credentialsRepo.findCredentialsByAccountId(accountId);
        if (credentials.isPresent()){
            return credentials.get();
        } else {
            throw new DAOException("Couldn't find Credentials object by accountID: " + accountId);
        }
    }

    @Override
    public void deleteCredentials(long accountId) {
        credentialsRepo.delete(findByAccountId(accountId));
    }


    public void save(Credentials credentials){
        try {
            findByAccountId(credentials.getAccountId());
        } catch (DAOException e){
            Credentials dbCredentials = new Credentials();
            dbCredentials.setAccountId(credentials.getAccountId());
            dbCredentials.setUsername(credentials.getUsername());
            dbCredentials.setUiLanguage(credentials.getUiLanguage());
            credentialsRepo.save(dbCredentials);
        }
        Credentials dbCredentials = findByAccountId(credentials.getAccountId());
        if (credentials.getDemographic() != null) {
            credentials.getDemographic().setCredentialsId(dbCredentials);
            dbCredentials.setDemographic(credentials.getDemographic());

        } if (credentials.getDescription() != null) {
            credentials.getDescription().setCredentials(dbCredentials);
            dbCredentials.setDescription(credentials.getDescription());

        } if (credentials.getProgress() != null) {
            credentials.getProgress().setCredentials(dbCredentials);
            dbCredentials.setProgress(credentials.getProgress());

        } if (credentials.getGames() != null) {
            for (Game game : credentials.getGames()) {
                game.setCredentials(dbCredentials);
                dbCredentials.getGames().add(game);
            }
        } credentialsRepo.save(dbCredentials);
    }
}
