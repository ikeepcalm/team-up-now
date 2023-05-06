package dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.settings;

import dev.ua.ikeepcalm.teamupnow.aop.annotations.I18N;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.SimpleCallback;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.MultiMessage;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.PurgeMessage;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.tools.LocaleTool;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class SettingsDeleteResponse extends SimpleCallback {
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
