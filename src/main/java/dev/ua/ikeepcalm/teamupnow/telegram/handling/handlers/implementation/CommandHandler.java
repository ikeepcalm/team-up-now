package dev.ua.ikeepcalm.teamupnow.telegram.handling.handlers.implementation;

import dev.ua.ikeepcalm.teamupnow.telegram.executing.responses.*;
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
    @Autowired
    private AboutResponse aboutResponse;
    @Autowired
    private MenuResponse menuResponse;

    @Override
    public void manage(Update update) {
        String command = update.getMessage().getText();
        switch (command) {
            case "/start" -> startResponse.execute(update);
            case "/games" -> gamesResponse.execute(update);
            case "/age" -> ageResponse.execute(update);
            case "/about" -> aboutResponse.execute(update);
            case "/menu" -> menuResponse.execute(update);
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
