package dev.ua.ikeepcalm.teamupnow.telegram.handlers.impls;

import dev.ua.ikeepcalm.teamupnow.telegram.handlers.Handler;
import dev.ua.ikeepcalm.teamupnow.telegram.handlers.subhandlers.SubHandler;
import dev.ua.ikeepcalm.teamupnow.telegram.services.TelegramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class CallbackHandler implements Handler {

    private final TelegramService telegramService;
    private final SubHandler gamesSubHandler;
    private final SubHandler ageSubHandler;
    private final SubHandler languageSubHandler;

    @Autowired
    public CallbackHandler(TelegramService telegramService, SubHandler gamesSubHandler, SubHandler ageSubHandler, SubHandler languageSubHandler) {
        this.telegramService = telegramService;
        this.gamesSubHandler = gamesSubHandler;
        this.ageSubHandler = ageSubHandler;
        this.languageSubHandler = languageSubHandler;
    }

    @Override
    public void manage(Update update) {
        String callback = update.getCallbackQuery().getData();
        if (callback.startsWith("profile-games")){
            gamesSubHandler.manage(update);
        } else if (callback.startsWith("profile-age-category")){
            ageSubHandler.manage(update);
        }
    }

    //TODO: Implement logic checking for sending non-existing callback / init callbacks
    @Override
    public boolean supports(Update update) {
        return true;
    }
}
