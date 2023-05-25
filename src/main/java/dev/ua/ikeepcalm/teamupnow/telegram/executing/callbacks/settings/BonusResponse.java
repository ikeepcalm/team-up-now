package dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.settings;

import dev.ua.ikeepcalm.teamupnow.database.entities.Credentials;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.ProgressENUM;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.SimpleCallback;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.MultiMessage;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.PurgeMessage;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.ResourceBundle;

@Component
public class BonusResponse extends SimpleCallback {

    @Override
    @Transactional
    public void manage(String receivedCallback, CallbackQuery origin) {
        ResourceBundle locale = getBundle(origin);
        Credentials credentials = credentialsService.findByAccountId(origin.getFrom().getId());
        credentials.getProgress().setProgressENUM(ProgressENUM.BONUS);
        credentialsService.save(credentials);
        MultiMessage multiMessage = new MultiMessage();
        multiMessage.setText(locale.getString("bonus-response"));
        multiMessage.setChatId(origin.getMessage().getChatId());
        telegramService.sendPurgeMessage(new PurgeMessage(origin.getMessage().getMessageId(), origin.getMessage().getChatId()));
        telegramService.sendMultiMessage(multiMessage);
    }
}
