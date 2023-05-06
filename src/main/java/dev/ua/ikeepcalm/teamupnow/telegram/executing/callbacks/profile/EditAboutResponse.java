package dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.profile;

import dev.ua.ikeepcalm.teamupnow.aop.annotations.I18N;
import dev.ua.ikeepcalm.teamupnow.database.entities.Credentials;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.ProgressENUM;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.SimpleCallback;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.MultiMessage;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.PurgeMessage;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.tools.LocaleTool;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class EditAboutResponse extends SimpleCallback {
    private LocaleTool locale;
    @I18N
    @Override
    public void manage(String receivedCallback, Message origin) {
        telegramService.sendPurgeMessage(new PurgeMessage(origin.getMessageId(), origin.getChatId()));
        Credentials credentials = credentialsService.findByAccountId(origin.getChatId());
        credentials.getProgress().setProgressENUM(ProgressENUM.ABOUT);
        credentialsService.save(credentials);
        MultiMessage message = new MultiMessage();
        message.setText(locale.getMessage("profile-description"));
        message.setChatId(origin.getChatId());
        telegramService.sendMultiMessage(message);
    }
}