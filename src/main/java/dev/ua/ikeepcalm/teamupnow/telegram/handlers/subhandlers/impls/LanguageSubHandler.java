package dev.ua.ikeepcalm.teamupnow.telegram.handlers.subhandlers.impls;

import dev.ua.ikeepcalm.teamupnow.telegram.commands.impls.ProfileCommand;
import dev.ua.ikeepcalm.teamupnow.telegram.handlers.subhandlers.SubHandler;
import dev.ua.ikeepcalm.teamupnow.telegram.models.ToDelete;
import org.telegram.telegrambots.meta.api.objects.Update;

public class LanguageSubHandler implements SubHandler {
    @Override
    public void manage(Update update) {
        String currentCallback = update.getCallbackQuery().getData();
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        int messageId = update.getCallbackQuery().getMessage().getMessageId();
        try {
            if (currentCallback.equals("profile-language-english")) {
                //TODO: Save main language and use it afterwards
            } else if (currentCallback.equals("profile-language-ukrainian")) {
                //TODO: Save main language and use it afterwards
            }
        } finally {
            new ProfileCommand().executeGames(chatId);
            telegramService.deleteCallback(new ToDelete(messageId,chatId));
        }
    }
}

