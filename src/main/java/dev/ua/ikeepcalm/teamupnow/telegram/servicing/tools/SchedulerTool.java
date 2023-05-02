package dev.ua.ikeepcalm.teamupnow.telegram.servicing.tools;

import dev.ua.ikeepcalm.teamupnow.database.dao.service.impls.CredentialsService;
import dev.ua.ikeepcalm.teamupnow.database.entities.Credentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SchedulerTool {

    @Autowired
    private CredentialsService credentialsService;

    @Scheduled(cron = "0 0 0 * * *")
    public void executeDailyTask() {
        List<Credentials> credentialsList = credentialsService.findAll();
        for (Credentials credentials : credentialsList) {
            credentials.setConnectionTokens(credentials.getSustainableTokens());
        }
        credentialsService.saveAll(credentialsList);
    }
}
