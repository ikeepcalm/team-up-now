package dev.ua.ikeepcalm.teamupnow.database.dao.service.impls;

import dev.ua.ikeepcalm.teamupnow.database.dao.repo.LanguageRepo;
import dev.ua.ikeepcalm.teamupnow.database.dao.repo.ProgressRepo;
import dev.ua.ikeepcalm.teamupnow.database.dao.service.DatabaseService;
import dev.ua.ikeepcalm.teamupnow.database.entities.Credentials;
import dev.ua.ikeepcalm.teamupnow.database.entities.Language;
import dev.ua.ikeepcalm.teamupnow.database.entities.Progress;
import dev.ua.ikeepcalm.teamupnow.database.exceptions.DAOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProgressService implements DatabaseService<Progress> {

    @Autowired
    ProgressRepo progressRepo;
    @Autowired
    CredentialsService credentialsService;

    @Override
    public Progress findByAccountId(long accountId) {
        Credentials credentials = credentialsService.findByAccountId(accountId);
        Optional<Progress> progress = progressRepo.findProgressByCredentialsId(credentials.getId());
        if (progress.isPresent()){
            return progress.get();
        } else {
            throw new DAOException("Couldn't find Progress object by accountID: " + accountId);
        }
    }
}
