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
public class SettingsResponse extends SimpleCallback {

    @Value("${img.settings}")
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
        InlineKeyboardButton deletion = new InlineKeyboardButton();
        deletion.setText(locale.getString("delete-account"));
        deletion.setCallbackData("menu-settings-delete-account");
        InlineKeyboardButton promo = new InlineKeyboardButton();
        promo.setText(locale.getString("bonus"));
        promo.setCallbackData("menu-settings-bonus");

        InlineKeyboardButton back = new InlineKeyboardButton();
        back.setText(locale.getString("menu-back"));
        back.setCallbackData("menu-back");
        firstRow.add(deletion);
        firstRow.add(promo);
        secondRow.add(back);
        keyboard.add(firstRow);
        keyboard.add(secondRow);
        inlineKeyboardMarkup.setKeyboard(keyboard);
        alterMessage.setReplyKeyboard(inlineKeyboardMarkup);
        telegramService.sendAlterMessage(alterMessage);
    }
}
