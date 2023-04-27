package dev.ua.ikeepcalm.teamupnow.database.dao.service.impls;

import dev.ua.ikeepcalm.teamupnow.database.dao.repo.CredentialsRepo;
import dev.ua.ikeepcalm.teamupnow.database.dao.service.iCredentials;
import dev.ua.ikeepcalm.teamupnow.database.entities.Credentials;
import dev.ua.ikeepcalm.teamupnow.database.entities.Game;
import dev.ua.ikeepcalm.teamupnow.database.exceptions.DAOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CredentialsService implements iCredentials<Credentials> {

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

    @Override
    public List<Credentials> findAll(){
        return (List<Credentials>) credentialsRepo.findAll();
    }

    public List<Credentials> findAllExcept(Long excludedId){
        return credentialsRepo.findAllExcept(excludedId);
    }

    public void save(Credentials credentials){
        try {
            findByAccountId(credentials.getAccountId());
        } catch (DAOException e){
            Credentials dbCredentials = new Credentials();
            dbCredentials.setAccountId(credentials.getAccountId());
            dbCredentials.setName(credentials.getName());
            dbCredentials.setUsername(credentials.getUsername());
            dbCredentials.setUiLanguage(credentials.getUiLanguage());
            dbCredentials.setSustainableTokens(5);
            dbCredentials.setConnectionTokens(5);
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
