package dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.menu;

import dev.ua.ikeepcalm.teamupnow.aop.annotations.I18N;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.SimpleCallback;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.tools.LocaleTool;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.AlterMessage;
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
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(locale.getMessage("more-delimiter"))
                .append("\n").append("\n")
                .append(locale.getMessage("more-support"))
                .append("<a href = \"https://t.me/ikeepcalm\">")
                .append(locale.getMessage("more-open-ticket"))
                .append("</a>")
                .append("\n")
                .append(locale.getMessage("more-gratuity"))
                .append("<a href = \"https://donatello.to/teamupnow\">")
                .append(locale.getMessage("more-support-development"))
                .append("</a>")
                .append("\n")
                .append(locale.getMessage("more-news"))
                .append("<a href = \"https://t.me/teamupnow_upd\">")
                .append(locale.getMessage("more-all-news"))
                .append("</a>")
                .append("\n")
                .append(locale.getMessage("more-update"))
                .append("28.04.2023")
                .append("\n")
                .append("\n")
                .append(locale.getMessage("more-delimiter"));
        alterMessage.setText(stringBuilder.toString());
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
