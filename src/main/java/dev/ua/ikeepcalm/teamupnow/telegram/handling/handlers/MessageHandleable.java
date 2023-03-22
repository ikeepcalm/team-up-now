package dev.ua.ikeepcalm.teamupnow.telegram.handling.handlers;

import dev.ua.ikeepcalm.teamupnow.aop.annotations.Progressable;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.ProgressENUM;
import dev.ua.ikeepcalm.teamupnow.telegram.handling.Handleable;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.messages.AboutMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class MessageHandleable implements Handleable {

    @Autowired
    private AboutMessage aboutMessage;

    @Override
    @Progressable(ProgressENUM.ABOUT)
    public void manage(Update update) {
        Message origin = update.getMessage();
        aboutMessage.execute(origin);
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