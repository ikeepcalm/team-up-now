package dev.ua.ikeepcalm.teamupnow.telegram.handling.handlers.implementation;

import dev.ua.ikeepcalm.teamupnow.telegram.executing.responses.AgeResponse;
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

    @Autowired
    private AgeResponse ageResponse;

    @Override
    public void manage(Update update) {
        String command = update.getMessage().getText();
        switch (command) {
            case "/start" -> startResponse.execute(update);
            case "/games" -> gamesResponse.execute(update);
            case "/age" -> ageResponse.execute(update);
        }
    }

    @Override
    public boolean supports(Update update) {
        return update.getMessage() != null;
    }
}
