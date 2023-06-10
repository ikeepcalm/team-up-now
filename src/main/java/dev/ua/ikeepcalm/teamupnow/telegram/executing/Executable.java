package dev.ua.ikeepcalm.teamupnow.telegram.executing;

import dev.ua.ikeepcalm.teamupnow.database.dao.service.impls.CredentialsService;
import dev.ua.ikeepcalm.teamupnow.database.dao.service.impls.MatchService;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.TelegramService;
import org.apache.logging.slf4j.SLF4JLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ResourceBundle;

@Component
public abstract class Executable<T> {

    protected TelegramService telegramService;
    protected CredentialsService credentialsService;
    protected MatchService matchService;
    protected Logger logger;

    @Autowired
    private void init(TelegramService telegramService,
                      CredentialsService credentialsService,
                      MatchService matchService){
        this.telegramService = telegramService;
        this.credentialsService = credentialsService;
        this.matchService = matchService;
        this.logger = LoggerFactory.getLogger(SLF4JLogger.class);
    }

    public abstract ResourceBundle getBundle(T origin);
}
