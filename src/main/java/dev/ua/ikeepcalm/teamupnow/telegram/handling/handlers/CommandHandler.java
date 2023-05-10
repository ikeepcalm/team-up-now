package dev.ua.ikeepcalm.teamupnow.telegram.handling.handlers;

import dev.ua.ikeepcalm.teamupnow.telegram.handling.Handleable;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.mediators.CommandMediator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class CommandHandler implements Handleable {

    @Autowired
    private CommandMediator commandMediator;

    @Override
    public void manage(Update update) {
        Message origin = update.getMessage();
        switch (origin.getText()) {
            case "/start" -> commandMediator.executeStartCommand(origin);
            case "/games" -> commandMediator.executeGamesCommand(origin);
            case "/age" -> commandMediator.executeAgeCommand(origin);
            case "/about" -> commandMediator.executeAboutCommand(origin);
            case "/menu" -> commandMediator.executeMenuCommand(origin);
            case "/notify" -> commandMediator.executeNotifyCommand(origin);
        }
    }

    @Override
    public boolean supports(Update update) {
        if (update.getMessage() != null){
            return update.getMessage().getText().startsWith("/");
        } else {
            return false;
        }
    }
}
