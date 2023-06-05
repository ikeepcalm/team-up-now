package dev.ua.ikeepcalm.teamupnow.telegram.servicing.tools;

import dev.ua.ikeepcalm.teamupnow.database.dao.service.impls.CredentialsService;
import dev.ua.ikeepcalm.teamupnow.database.dao.service.impls.PersistentService;
import dev.ua.ikeepcalm.teamupnow.database.entities.Bonus;
import dev.ua.ikeepcalm.teamupnow.database.entities.Credentials;
import dev.ua.ikeepcalm.teamupnow.database.entities.Persistent;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.LanguageENUM;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.ProgressENUM;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.TelegramService;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.MultiMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.spi.SLF4JServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class SchedulerTool {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private final PersistentService persistentService;
    private final CredentialsService credentialsService;
    private final TelegramService telegramService;
    private final BonusTool bonusTool;

    @Autowired
    public SchedulerTool(CredentialsService credentialsService, PersistentService persistentService, TelegramService telegramService, BonusTool bonusTool) {
        this.credentialsService = credentialsService;
        this.persistentService = persistentService;
        this.telegramService = telegramService;
        this.bonusTool = bonusTool;
    }

    @Scheduled(cron = "0 0 0 1/3 * ?")
    public void executeNotificationForDelayed(){
        ResourceBundle locale;
        List<Credentials> credentialsList = credentialsService.findAll();
        for (Credentials credentials : credentialsList) {
            if (credentials.getProgress().getProgressENUM() != ProgressENUM.DONE) {
                if (credentials.getUiLanguage() == LanguageENUM.UKRAINIAN) {
                    locale = ResourceBundle.getBundle("i18n.messages_uk_UK");
                } else {
                    locale = ResourceBundle.getBundle("i18n.messages_en_GB");
                }
                MultiMessage multiMessage = new MultiMessage();
                multiMessage.setText(locale.getString("inactive-user-notification") + "\n" + bonusTool.generateBonusCode());
                multiMessage.setChatId(credentials.getAccountId());
                telegramService.sendMultiMessage(multiMessage);
            }
        }
    }



    @Scheduled(cron = "0 0 21 * * *")
    public void executeDailyTask() {
        List<Credentials> credentialsList = credentialsService.findAll();
        Persistent persistent = persistentService.findPersistent();
        ResourceBundle locale;

        Iterator<Credentials> iterator = credentialsList.iterator();
        while (iterator.hasNext()) {
            Credentials credentials = iterator.next();
            try {
                if (credentials.getUiLanguage() == LanguageENUM.UKRAINIAN){
                    locale = ResourceBundle.getBundle("i18n.messages_uk_UK");
                } else {
                    locale = ResourceBundle.getBundle("i18n.messages_en_GB");
                }
                MultiMessage resetMessage = new MultiMessage();
                resetMessage.setChatId(credentials.getAccountId());
                resetMessage.setText(locale.getString("daily-update"));

                MultiMessage newbiesMessage = new MultiMessage();
                newbiesMessage.setChatId(credentials.getAccountId());
                newbiesMessage.setText(locale.getString("daily-update-statistics-first-part") + " " +
                        (credentialsList.size() - persistent.getTotalUsers()) + " " +
                        locale.getString("daily-update-statistics-second-part"));

                credentials.setConnectionTokens(credentials.getSustainableTokens());

                telegramService.sendMultiMessage(resetMessage);
                telegramService.sendMultiMessage(newbiesMessage);
            } catch (Exception e) {
                iterator.remove();
                credentialsService.deleteCredentials(credentials.getAccountId());
                LOGGER.info("User to purge <" + credentials.getUsername() + ", " + credentials.getAccountId() + ">. Done!");
            }
        }

        persistent.setTotalUsers(credentialsList.size());
        persistentService.save(persistent);
        credentialsService.saveAll(credentialsList);
    }
}
