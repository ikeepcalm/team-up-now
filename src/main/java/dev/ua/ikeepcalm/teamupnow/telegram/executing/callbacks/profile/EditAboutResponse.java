package dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.profile;

import dev.ua.ikeepcalm.teamupnow.database.entities.Credentials;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.ProgressENUM;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.SimpleCallback;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.MultiMessage;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.PurgeMessage;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.ResourceBundle;

@Component
public class EditAboutResponse extends SimpleCallback {
    @Override
    public void manage(String receivedCallback, CallbackQuery origin) {
        ResourceBundle locale = getBundle(origin);
        telegramService.sendPurgeMessage(new PurgeMessage(origin.getMessage().getMessageId(), origin.getMessage().getChatId()));
        Credentials credentials = credentialsService.findByAccountId(origin.getMessage().getChatId());
        credentials.getProgress().setProgressENUM(ProgressENUM.ABOUT);
        credentialsService.save(credentials);
        MultiMessage message = new MultiMessage();
        message.setText(locale.getString("profile-description"));
        message.setChatId(origin.getMessage().getChatId());
        telegramService.sendMultiMessage(message);
    }
}