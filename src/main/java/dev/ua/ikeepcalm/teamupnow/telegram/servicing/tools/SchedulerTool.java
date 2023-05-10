package dev.ua.ikeepcalm.teamupnow.telegram.servicing.tools;

import dev.ua.ikeepcalm.teamupnow.database.dao.service.impls.CredentialsService;
import dev.ua.ikeepcalm.teamupnow.database.dao.service.impls.PersistentService;
import dev.ua.ikeepcalm.teamupnow.database.entities.Credentials;
import dev.ua.ikeepcalm.teamupnow.database.entities.Persistent;
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
    private PersistentService persistentService;
    @Autowired
    private CredentialsService credentialsService;
    @Autowired
    private TelegramService telegramService;

    @Scheduled(cron = "0 0 0 * * *")
    public void executeDailyTask() {
        List<Credentials> credentialsList = credentialsService.findAll();
        Persistent persistent = persistentService.findPersistent();
        LocaleTool localeTool;
        for (Credentials credentials : credentialsList) {
            try {
                if (credentials.getUiLanguage() == LanguageENUM.UKRAINIAN){
                    localeTool = new LocaleTool("i18n/messages_ua.properties");
                } else {
                    localeTool = new LocaleTool("i18n/messages_ua.properties");
                }
                MultiMessage resetMessage = new MultiMessage();
                resetMessage.setChatId(credentials.getAccountId());
                resetMessage.setText(localeTool.getMessage("daily-update"));
                MultiMessage newbiesMessage = new MultiMessage();
                newbiesMessage.setChatId(credentials.getAccountId());
                newbiesMessage.setText(
                        localeTool.getMessage("daily-update-statistics-first-part") +
                        (credentialsList.size() - persistent.getTotalUsers()) +
                        localeTool.getMessage("daily-update-statistics-second-part")
                ); credentials.setConnectionTokens(credentials.getSustainableTokens());
                telegramService.sendMultiMessage(resetMessage);
                telegramService.sendMultiMessage(newbiesMessage);
            } catch (Exception e) {
                credentialsService.deleteCredentials(credentials.getAccountId());
                credentialsList.remove(credentials);
                LOGGER.info("Bot was blocked by user [" + credentials.getUsername() + " ~ " + credentials.getName() + "]. Deleting him from credentials list...");
            }
        }persistent.setTotalUsers(credentialsList.size());
        persistentService.save(persistent);
        credentialsService.saveAll(credentialsList);
    }
}
