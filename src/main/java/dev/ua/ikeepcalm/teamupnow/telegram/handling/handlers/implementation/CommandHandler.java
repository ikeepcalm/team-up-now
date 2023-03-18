package dev.ua.ikeepcalm.teamupnow.telegram.handling.handlers.implementation;

import dev.ua.ikeepcalm.teamupnow.telegram.executing.responses.GamesResponse;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.responses.StartResponse;
import dev.ua.ikeepcalm.teamupnow.telegram.handling.handlers.Handler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class CommandHandler implements Handler {

    @Autowired
    private StartResponse startResponse;

    @Autowired
    private GamesResponse gamesResponse;

    @Override
    public void manage(Update update) {
        String command = update.getMessage().getText();
        if (command.equals("/start")) {
            startResponse.execute(update);
        } else if(command.equals("/profile")){
            gamesResponse.execute(update);
        }
    }

    @Override
    public boolean supports(Update update) {
        return update.getMessage().getText().startsWith("/");
    }
}
