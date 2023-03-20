package dev.ua.ikeepcalm.teamupnow.telegram.handling.handlers.implementation;

import dev.ua.ikeepcalm.teamupnow.telegram.handling.handlers.Handler;
import dev.ua.ikeepcalm.teamupnow.telegram.handling.subhandlers.implementation.AboutSubHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class MessageHandler implements Handler {

    @Autowired
    private AboutSubHandler aboutSubHandler;

    @Override
    public void manage(Update update) {
        aboutSubHandler.manage(update);
    }

    @Override
    public boolean supports(Update update) {
        if (update.getMessage() != null){
            if (!update.getMessage().getText().startsWith("/")){
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}