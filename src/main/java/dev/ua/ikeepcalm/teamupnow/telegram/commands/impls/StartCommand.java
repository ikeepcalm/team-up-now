package dev.ua.ikeepcalm.teamupnow.telegram.commands.impls;

import dev.ua.ikeepcalm.teamupnow.telegram.commands.Command;
import dev.ua.ikeepcalm.teamupnow.telegram.models.Callback;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class StartCommand implements Command {

    public void determineCode(Update update) {
        String languageCode = update.getMessage().getFrom().getLanguageCode(); //uk
        if (languageCode.equals("uk")){
            //TODO: Auto save Ukrainian as a primary language for new-created user
        } else {
            //TODO: Auto save English as a primary language for new-created user
        }
    }

    public void execute(Long chatId) {
        Callback callback = new Callback();
        callback.setText("""
                Nice to meet you here!

                It's time to get acquainted so I know what kind of friends to find for you!

                To start, click the button that appears below the text entry field.""");
        callback.setChatId(chatId);
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add("/profile");
        keyboardRows.add(row);
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        callback.setReplyKeyboard(replyKeyboardMarkup);
        telegramService.sendCallback(callback);
    }
}
