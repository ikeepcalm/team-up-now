package dev.ua.ikeepcalm.teamupnow.telegram.handlers.subhandlers.impls;

import dev.ua.ikeepcalm.teamupnow.telegram.commands.impls.ProfileCommand;
import dev.ua.ikeepcalm.teamupnow.telegram.handlers.subhandlers.SubHandler;
import dev.ua.ikeepcalm.teamupnow.telegram.models.EditMessage;
import dev.ua.ikeepcalm.teamupnow.telegram.models.ToDelete;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

@Component
public class GamesSubHandler implements SubHandler {
    @Override
    public void manage(Update update) {
        String currentCallback = update.getCallbackQuery().getData();
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        Message origin = update.getCallbackQuery().getMessage();
        InlineKeyboardMarkup markup = origin.getReplyMarkup();
        if (!currentCallback.equals("profile-games-ready")) {
            try {
                if (currentCallback.startsWith("profile-games-minecraft")) {
                    updateButton("profile-games-minecraft", markup, "Minecraft");
                } else if (currentCallback.startsWith("profile-games-csgo")) {
                    updateButton("profile-games-csgo", markup, "CS:GO");
                } else if (currentCallback.startsWith("profile-games-destiny2")) {
                    updateButton("profile-games-destiny2", markup, "Destiny 2");
                }
            } finally {
                EditMessage editMessage = new EditMessage();
                editMessage.setMessageId(origin.getMessageId());
                editMessage.setChatId(origin.getChatId());
                editMessage.setReplyKeyboard(markup);
                telegramService.sendEditMessage(editMessage);
            }
        } else {
            //TODO: Save games
            telegramService.deleteCallback(new ToDelete(update.getCallbackQuery().getMessage().getMessageId(), chatId));
            new ProfileCommand().executeAge(chatId);
        }
    }

    private void updateButton(String callbackData, InlineKeyboardMarkup markup, String buttonText) {
        for (List<InlineKeyboardButton> row : markup.getKeyboard()) {
            for (InlineKeyboardButton button : row) {
                if (button.getCallbackData().startsWith(callbackData)) {
                    if (button.getCallbackData().endsWith("-chosen")) {
                        button.setText(buttonText);
                        button.setCallbackData(callbackData);
                    } else {
                        button.setText(buttonText + " ✓");
                        button.setCallbackData(callbackData + "-chosen");
                    }
                }
            }
        }
    }
}
