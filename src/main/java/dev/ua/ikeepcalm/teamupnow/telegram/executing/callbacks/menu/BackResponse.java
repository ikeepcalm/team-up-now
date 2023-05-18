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
public class BackResponse extends SimpleCallback {

    @Value("${img.menu}")
    String filePath;

    @Override
    public void manage(String receivedCallback, CallbackQuery origin) {
        ResourceBundle locale = getBundle(origin);
        AlterMessage alterMessage = new AlterMessage();
        alterMessage.setFilePath(filePath);
        alterMessage.setMessageId(origin.getMessage().getMessageId());
        alterMessage.setChatId(origin.getMessage().getChatId());
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        List<InlineKeyboardButton> secondRow = new ArrayList<>();
        InlineKeyboardButton profile = new InlineKeyboardButton();
        profile.setText(locale.getString("menu-profile"));
        profile.setCallbackData("menu-profile");
        InlineKeyboardButton discover = new InlineKeyboardButton();
        discover.setText(locale.getString("menu-discover"));
        discover.setCallbackData("menu-discover");
        InlineKeyboardButton settings = new InlineKeyboardButton();
        settings.setText(locale.getString("menu-settings"));
        settings.setCallbackData("menu-settings");
        InlineKeyboardButton more = new InlineKeyboardButton();
        more.setText(locale.getString("menu-more"));
        more.setCallbackData("menu-more");
        firstRow.add(profile);
        firstRow.add(discover);
        secondRow.add(settings);
        secondRow.add(more);
        keyboard.add(firstRow);
        keyboard.add(secondRow);
        inlineKeyboardMarkup.setKeyboard(keyboard);
        alterMessage.setReplyKeyboard(inlineKeyboardMarkup);
        telegramService.sendAlterMessage(alterMessage);
    }
}
