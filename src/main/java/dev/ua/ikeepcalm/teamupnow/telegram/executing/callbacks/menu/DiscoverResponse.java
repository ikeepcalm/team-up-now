package dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.menu;

import dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.SimpleCallback;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.AlterMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class DiscoverResponse extends SimpleCallback {

    @Value("${img.discover}")
    String filePath;
    @Override
    public void manage(String receivedCallback, CallbackQuery origin) {
        ResourceBundle locale = getBundle(origin);
        AlterMessage alterMessage = new AlterMessage();
        alterMessage.setFilePath(filePath);
        alterMessage.setMessageId(origin.getMessage().getMessageId());
        alterMessage.setChatId(origin.getMessage().getChatId());
        alterMessage.setText(locale.getString("discover-main"));
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        List<InlineKeyboardButton> secondRow = new ArrayList<>();
        InlineKeyboardButton explore = new InlineKeyboardButton();
        explore.setText(locale.getString("discover-main-explore"));
        explore.setCallbackData("explore");
        InlineKeyboardButton matches = new InlineKeyboardButton();
        matches.setText(locale.getString("discover-main-matches"));
        matches.setCallbackData("matches");
        InlineKeyboardButton back = new InlineKeyboardButton();
        back.setText(locale.getString("menu-back"));
        back.setCallbackData("menu-back");
        firstRow.add(explore);
        firstRow.add(matches);
        secondRow.add(back);
        keyboard.add(firstRow);
        keyboard.add(secondRow);
        inlineKeyboardMarkup.setKeyboard(keyboard);
        alterMessage.setReplyKeyboard(inlineKeyboardMarkup);
        telegramService.sendAlterMessage(alterMessage);
    }
}
