package dev.ua.ikeepcalm.teamupnow.database.dao.service.impls;

import dev.ua.ikeepcalm.teamupnow.database.dao.repo.DescriptionRepo;
import dev.ua.ikeepcalm.teamupnow.database.dao.service.DatabaseService;
import dev.ua.ikeepcalm.teamupnow.database.entities.Credentials;
import dev.ua.ikeepcalm.teamupnow.database.entities.Description;
import dev.ua.ikeepcalm.teamupnow.database.exceptions.DAOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DescriptionService implements DatabaseService<Description> {

    @Autowired
    DescriptionRepo descriptionRepo;
    @Autowired
    CredentialsService credentialsService;

    @Override
    public Description findByAccountId(long accountId) {
        Credentials credentials = credentialsService.findByAccountId(accountId);
        Optional<Description> description = descriptionRepo.findDescriptionByCredentialsId(credentials.getId());
        if (description.isPresent()){
            return description.get();
        } else {
            throw new DAOException("Couldn't find Description object by accountID: " + accountId);
        }
    }
}
