package dev.ua.ikeepcalm.teamupnow.telegram.servicing.tools;

import dev.ua.ikeepcalm.teamupnow.database.dao.service.impls.CredentialsService;
import dev.ua.ikeepcalm.teamupnow.database.entities.Credentials;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.LanguageENUM;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.TelegramService;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.MultiMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.spi.SLF4JServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SchedulerTool {

    private static final Logger LOGGER = LoggerFactory.getLogger(SLF4JServiceProvider.class);

    @Autowired
    private CredentialsService credentialsService;

    @Autowired
    private TelegramService telegramService;

    @Scheduled(cron = "0 0 0 * * *")
    public void executeDailyTask() {
        List<Credentials> credentialsList = credentialsService.findAll();
        MultiMessage multiMessage = new MultiMessage();
        LocaleTool localeTool;
        for (Credentials credentials : credentialsList) {
            try {
                if (credentials.getUiLanguage() == LanguageENUM.UKRAINIAN){
                    localeTool = new LocaleTool("i18n/messages_ua.properties");
                } else {
                    localeTool = new LocaleTool("i18n/messages_ua.properties");
                }
                multiMessage.setChatId(credentials.getAccountId());
                multiMessage.setText(localeTool.getMessage("daily-update"));
                credentials.setConnectionTokens(credentials.getSustainableTokens());
                telegramService.sendMultiMessage(multiMessage);
            } catch (Exception e) {
                credentialsService.deleteCredentials(credentials.getAccountId());
                LOGGER.info("Bot was blocked by user [" + credentials + "]. Deleting him from credentials list...");
            }
        }
        credentialsService.saveAll(credentialsList);
    }
}
