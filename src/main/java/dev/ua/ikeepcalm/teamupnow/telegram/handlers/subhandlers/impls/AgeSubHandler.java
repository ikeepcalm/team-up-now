package dev.ua.ikeepcalm.teamupnow.telegram.handlers.subhandlers.impls;

import dev.ua.ikeepcalm.teamupnow.telegram.commands.impls.ProfileCommand;
import dev.ua.ikeepcalm.teamupnow.telegram.handlers.subhandlers.SubHandler;
import dev.ua.ikeepcalm.teamupnow.telegram.models.ToDelete;
import org.telegram.telegrambots.meta.api.objects.Update;

public class AgeSubHandler implements SubHandler {
    @Override
    public void manage(Update update) {
        String currentCallback = update.getCallbackQuery().getData();
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        int messageId = update.getCallbackQuery().getMessage().getMessageId();
        try {
            if (currentCallback.equals("profile-age-category-young")) {
                //TODO: Save age category
            } else if (currentCallback.equals("profile-age-category-young-adult")) {
                //TODO: Save age category
            } else if (currentCallback.equals("profile-age-category-adult")){
                //TODO: Save age category
            }
        } finally {
            new ProfileCommand().executeAbout(chatId);
            telegramService.deleteCallback(new ToDelete(messageId,chatId));
            //TODO: Users' entities in database, especially value of their current step of filling up the init info
            // to eliminate possible vulnerabilities and to continue developing bot
        }
    }
}
