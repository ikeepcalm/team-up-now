package dev.ua.ikeepcalm.teamupnow.telegram.handling.handlers.implementation;

import dev.ua.ikeepcalm.teamupnow.telegram.handling.handlers.Handler;
import dev.ua.ikeepcalm.teamupnow.telegram.handling.subhandlers.AgeSubHandler;
import dev.ua.ikeepcalm.teamupnow.telegram.handling.subhandlers.implementation.GamesSubHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class CallbackHandler implements Handler {

    @Autowired
    private GamesSubHandler gamesSubHandler;

    @Autowired
    private AgeSubHandler ageSubHandler;

    @Override
    public void manage(Update update) {
        String callback = update.getCallbackQuery().getData();
        if (callback.startsWith("profile-games")) {
            gamesSubHandler.manage(update);
        } else if (callback.startsWith("profile-age-category")){
            ageSubHandler.manage(update);
        }
    }

    @Override
    public boolean supports(Update update) {
        return update.getCallbackQuery() != null;
    }
}