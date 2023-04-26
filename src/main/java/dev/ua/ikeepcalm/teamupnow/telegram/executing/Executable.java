package dev.ua.ikeepcalm.teamupnow.telegram.executing;

import dev.ua.ikeepcalm.teamupnow.database.dao.service.impls.CredentialsService;
import dev.ua.ikeepcalm.teamupnow.database.dao.service.impls.MatchService;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.TelegramService;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.mediators.CommandMediator;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.mediators.ResponseMediator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Executable {

    protected TelegramService telegramService;
    protected CredentialsService credentialsService;
    protected MatchService matchService;

    @Autowired
    private void init(TelegramService telegramService, CredentialsService credentialsService,
                MatchService matchService){
        this.telegramService = telegramService;
        this.credentialsService = credentialsService;
        this.matchService = matchService;
    }

}
