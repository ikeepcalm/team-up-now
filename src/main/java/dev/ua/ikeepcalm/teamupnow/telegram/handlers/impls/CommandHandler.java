package dev.ua.ikeepcalm.teamupnow.telegram.handlers.impls;

import dev.ua.ikeepcalm.teamupnow.telegram.commands.impls.ProfileCommand;
import dev.ua.ikeepcalm.teamupnow.telegram.commands.impls.StartCommand;
import dev.ua.ikeepcalm.teamupnow.telegram.handlers.Handler;
import dev.ua.ikeepcalm.teamupnow.telegram.services.TelegramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class CommandHandler implements Handler {

    private final TelegramService telegramService;

    @Autowired
    public CommandHandler(TelegramService telegramService) {
        this.telegramService = telegramService;
    }

    @Override
    public void manage(Update update) {
        String command = update.getMessage().getText();
        if (command.equals("/start")) {
            new StartCommand().determineCode(update);
            new StartCommand().execute(update.getMessage().getChatId());
        } else if (command.equals("/profile")) {
            new ProfileCommand().executeGames(update.getMessage().getChatId());
        }
    }

    //TODO: Implement logic checking for sending prohibited commands
    @Override
    public boolean supports(Update update) {
        return true;
    }
}
