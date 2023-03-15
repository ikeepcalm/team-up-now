package dev.ua.ikeepcalm.teamupnow.database.dao.service.impls;

import dev.ua.ikeepcalm.teamupnow.database.dao.repo.LanguageRepo;
import dev.ua.ikeepcalm.teamupnow.database.dao.service.DatabaseService;
import dev.ua.ikeepcalm.teamupnow.database.entities.Credentials;
import dev.ua.ikeepcalm.teamupnow.database.entities.Demographic;
import dev.ua.ikeepcalm.teamupnow.database.entities.Language;
import dev.ua.ikeepcalm.teamupnow.database.exceptions.DAOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LanguageService implements DatabaseService<Language> {

    @Autowired
    LanguageRepo languageRepo;
    @Autowired
    CredentialsService credentialsService;

    @Override
    public Language findByAccountId(long accountId) {
        Credentials credentials = credentialsService.findByAccountId(accountId);
        Optional<Language> language = languageRepo.findLanguageByCredentialsId(credentials.getId());
        if (language.isPresent()){
            return language.get();
        } else {
            throw new DAOException("Couldn't find Language object by accountID: " + accountId);
        }
    }
}
