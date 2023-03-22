package dev.ua.ikeepcalm.teamupnow.telegram.handling.handlers;

import dev.ua.ikeepcalm.teamupnow.telegram.executing.commands.AboutCommand;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.commands.AgeCommand;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.commands.GamesCommand;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.commands.MenuCommand;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.commands.StartCommand;
import dev.ua.ikeepcalm.teamupnow.telegram.handling.Handleable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class CommandHandleable implements Handleable {

    @Autowired
    private StartCommand startCommand;
    @Autowired
    private GamesCommand gamesCommand;
    @Autowired
    private AgeCommand ageCommand;
    @Autowired
    private AboutCommand aboutCommand;
    @Autowired
    private MenuCommand menuCommand;

    @Override
    public void manage(Update update) {
        Message origin = update.getMessage();
        switch (origin.getText()) {
            case "/start" -> startCommand.execute(origin);
            case "/games" -> gamesCommand.execute(origin);
            case "/age" -> ageCommand.execute(origin);
            case "/about" -> aboutCommand.execute(origin);
            case "/menu" -> menuCommand.execute(origin);
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
