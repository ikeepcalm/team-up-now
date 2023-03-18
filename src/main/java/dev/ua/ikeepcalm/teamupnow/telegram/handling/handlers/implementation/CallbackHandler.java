package dev.ua.ikeepcalm.teamupnow.telegram.handling.handlers.implementation;

import dev.ua.ikeepcalm.teamupnow.telegram.handling.handlers.Handler;
import org.telegram.telegrambots.meta.api.objects.Update;


public class CallbackHandler implements Handler {

    @Override
    public void manage(Update update) {
//        String callback = update.getCallbackQuery().getData();
//        if (callback.startsWith("profile-games")){
//            gamesSubHandler.manage(update);
//        } else if (callback.startsWith("profile-age-category")){
//            ageSubHandler.manage(update);
//        }
    }

    @Override
    public boolean supports(Update update) {
        return update.getCallbackQuery() != null;
    }
}
