package dev.ua.ikeepcalm.teamupnow.database.dao.service.impls;

import dev.ua.ikeepcalm.teamupnow.database.dao.repo.CredentialsRepo;
import dev.ua.ikeepcalm.teamupnow.database.dao.service.DatabaseService;
import dev.ua.ikeepcalm.teamupnow.database.entities.Credentials;
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
}
