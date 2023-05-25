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
public class MoreResponse extends SimpleCallback {

    @Value("${img.info}")
    String filePath;

    @Override
    public void manage(String receivedCallback, CallbackQuery origin) {
        ResourceBundle locale = getBundle(origin);
        AlterMessage alterMessage = new AlterMessage();
        alterMessage.setFilePath(filePath);
        alterMessage.setMessageId(origin.getMessage().getMessageId());
        alterMessage.setChatId(origin.getMessage().getChatId());
        alterMessage.setParseMode("html");
        String stringBuilder = locale.getString("more-delimiter") +
                "\n" + "\n" +
                locale.getString("more-support") +
                "<a href = \"https://t.me/ikeepcalm\">" +
                locale.getString("more-open-ticket") +
                "</a>" +
                "\n" +
                locale.getString("more-gratuity") +
                "<a href = \"https://donatello.to/teamupnow\">" +
                locale.getString("more-support-development") +
                "</a>" +
                "\n" +
                locale.getString("more-news") +
                "<a href = \"https://t.me/teamupnow_upd\">" +
                locale.getString("more-all-news") +
                "</a>" +
                "\n" +
                locale.getString("more-update") +
                "25.05.2023" +
                "\n" +
                "\n" +
                locale.getString("more-delimiter");
        alterMessage.setText(stringBuilder);
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        InlineKeyboardButton back = new InlineKeyboardButton();
        back.setText(locale.getString("menu-back"));
        back.setCallbackData("menu-back");
        firstRow.add(back);
        keyboard.add(firstRow);
        inlineKeyboardMarkup.setKeyboard(keyboard);
        alterMessage.setReplyKeyboard(inlineKeyboardMarkup);
        telegramService.sendAlterMessage(alterMessage);
    }
}
