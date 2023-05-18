package dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.settings;

import dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.SimpleCallback;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.MultiMessage;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.PurgeMessage;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.ResourceBundle;

@Component
public class SettingsDeleteResponse extends SimpleCallback {

    @Override
    @Transactional
    public void manage(String receivedCallback, CallbackQuery origin) {
        ResourceBundle locale = getBundle(origin);
        PurgeMessage purgeMessage = new PurgeMessage(origin.getMessage().getMessageId(), origin.getMessage().getChatId());
        MultiMessage multiMessage = new MultiMessage();
        multiMessage.setChatId(origin.getMessage().getChatId());
        multiMessage.setText(locale.getString("menu-settings-delete"));
        telegramService.sendPurgeMessage(purgeMessage);
        telegramService.sendMultiMessage(multiMessage);
        credentialsService.deleteCredentials(origin.getMessage().getChatId());
    }
}
