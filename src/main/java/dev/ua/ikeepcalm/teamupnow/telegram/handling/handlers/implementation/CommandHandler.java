package dev.ua.ikeepcalm.teamupnow.telegram.handling.handlers.implementation;

import dev.ua.ikeepcalm.teamupnow.telegram.executing.responses.GamesResponse;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.responses.StartResponse;
import dev.ua.ikeepcalm.teamupnow.telegram.handling.handlers.Handler;
import org.telegram.telegrambots.meta.api.objects.Update;

import static dev.ua.ikeepcalm.teamupnow.Application.ctx;


public class CommandHandler implements Handler {

    @Override
    public void manage(Update update) {
        Long chatId = update.getMessage().getChatId();
        String command = update.getMessage().getText();
        if (command.equals("/start")) {
            ctx.getBean(StartResponse.class).execute(update);
        } else if(command.equals("/profile")){
            ctx.getBean(GamesResponse.class).execute(update);
        }
    }

    @Override
    public boolean supports(Update update) {
        return update.getMessage().getText().startsWith("/");
    }
}
