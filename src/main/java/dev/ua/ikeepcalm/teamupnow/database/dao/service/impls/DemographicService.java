package dev.ua.ikeepcalm.teamupnow.database.dao.service.impls;

import dev.ua.ikeepcalm.teamupnow.database.dao.repo.CredentialsRepo;
import dev.ua.ikeepcalm.teamupnow.database.dao.repo.DemographicRepo;
import dev.ua.ikeepcalm.teamupnow.database.dao.service.DatabaseService;
import dev.ua.ikeepcalm.teamupnow.database.entities.Credentials;
import dev.ua.ikeepcalm.teamupnow.database.entities.Demographic;
import dev.ua.ikeepcalm.teamupnow.database.exceptions.DAOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DemographicService implements DatabaseService<Demographic> {

    @Autowired
    DemographicRepo demographicRepo;
    @Autowired
    CredentialsService credentialsService;

    @Override
    public Demographic findByAccountId(long accountId) {
        Credentials credentials = credentialsService.findByAccountId(accountId);
        Optional<Demographic> demographic = demographicRepo.findDemographicByCredentialsId(credentials.getId());
        if (demographic.isPresent()){
            return demographic.get();
        } else {
            throw new DAOException("Couldn't find Demographic object by accountID: " + accountId);
        }
    }
}
