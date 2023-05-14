package dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.menu;

import dev.ua.ikeepcalm.teamupnow.aop.annotations.I18N;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.SimpleCallback;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.AlterMessage;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.tools.LocaleTool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class MoreResponse extends SimpleCallback {

    @Value("${img.info}")
    String filePath;
    private LocaleTool locale;

    @I18N
    @Override
    public void manage(String receivedCallback, Message origin) {
        AlterMessage alterMessage = new AlterMessage();
        alterMessage.setFilePath(filePath);
        alterMessage.setMessageId(origin.getMessageId());
        alterMessage.setChatId(origin.getChatId());
        alterMessage.setParseMode("html");
        String stringBuilder = locale.getMessage("more-delimiter") +
                "\n" + "\n" +
                locale.getMessage("more-support") +
                "<a href = \"https://t.me/ikeepcalm\">" +
                locale.getMessage("more-open-ticket") +
                "</a>" +
                "\n" +
                locale.getMessage("more-gratuity") +
                "<a href = \"https://donatello.to/teamupnow\">" +
                locale.getMessage("more-support-development") +
                "</a>" +
                "\n" +
                locale.getMessage("more-news") +
                "<a href = \"https://t.me/teamupnow_upd\">" +
                locale.getMessage("more-all-news") +
                "</a>" +
                "\n" +
                locale.getMessage("more-update") +
                "14.05.2023" +
                "\n" +
                "\n" +
                locale.getMessage("more-delimiter");
        alterMessage.setText(stringBuilder);
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        InlineKeyboardButton back = new InlineKeyboardButton();
        back.setText(locale.getMessage("menu-back"));
        back.setCallbackData("menu-back");
        firstRow.add(back);
        keyboard.add(firstRow);
        inlineKeyboardMarkup.setKeyboard(keyboard);
        alterMessage.setReplyKeyboard(inlineKeyboardMarkup);
        telegramService.sendAlterMessage(alterMessage);
    }
}
