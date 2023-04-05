package dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.settings;

import dev.ua.ikeepcalm.teamupnow.aop.annotations.I18N;
import dev.ua.ikeepcalm.teamupnow.database.dao.service.impls.CredentialsService;
import dev.ua.ikeepcalm.teamupnow.database.entities.Credentials;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.LanguageENUM;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.Executable;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.TelegramService;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.implementations.LocaleTool;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.AlterMessage;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.MultiMessage;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.PurgeMessage;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class SettingsDeleteResponse implements Executable {

    @Autowired
    private CredentialsService credentialsService;
    @Autowired
    private TelegramService telegramService;
    private LocaleTool locale;

    @I18N
    @Override
    @Transactional
    public void manage(String receivedCallback, Message origin) {
        PurgeMessage purgeMessage = new PurgeMessage(origin.getMessageId(), origin.getChatId());
        MultiMessage multiMessage = new MultiMessage();
        multiMessage.setChatId(origin.getChatId());
        multiMessage.setText(locale.getMessage("menu-settings-delete"));
        telegramService.sendPurgeMessage(purgeMessage);
        telegramService.sendMultiMessage(multiMessage);
        credentialsService.deleteCredentials(origin.getChatId());
    }
}
