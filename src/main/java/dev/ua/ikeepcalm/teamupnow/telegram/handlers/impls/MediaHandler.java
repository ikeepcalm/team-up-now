package dev.ua.ikeepcalm.teamupnow.telegram.handlers.impls;

import dev.ua.ikeepcalm.teamupnow.telegram.handlers.Handler;
import dev.ua.ikeepcalm.teamupnow.telegram.models.Message;
import dev.ua.ikeepcalm.teamupnow.telegram.services.TelegramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class MediaHandler implements Handler {

    private final TelegramService telegramService;

    @Autowired
    public MediaHandler(TelegramService telegramService) {
        this.telegramService = telegramService;
    }

    @Override
    public void manage(Update update) {
        telegramService.sendMessage(new Message("Media recognized!", update.getMessage().getChatId()));
    }

    //TODO: Implement checking for prohibited media (NSFW, 18+, etc)
    @Override
    public boolean supports(Update update) {
        return true;
    }
}
