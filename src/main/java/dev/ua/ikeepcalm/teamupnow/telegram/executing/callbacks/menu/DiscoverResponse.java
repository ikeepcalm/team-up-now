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
public class DiscoverResponse extends SimpleCallback {

    @Value("${img.discover}")
    String filePath;
    private LocaleTool locale;

    @I18N
    @Override
    public void manage(String receivedCallback, Message origin) {
        AlterMessage alterMessage = new AlterMessage();
        alterMessage.setFilePath(filePath);
        alterMessage.setMessageId(origin.getMessageId());
        alterMessage.setChatId(origin.getChatId());
        alterMessage.setText(locale.getMessage("discover-main"));
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        List<InlineKeyboardButton> secondRow = new ArrayList<>();
        InlineKeyboardButton explore = new InlineKeyboardButton();
        explore.setText(locale.getMessage("discover-main-explore"));
        explore.setCallbackData("explore");
        InlineKeyboardButton matches = new InlineKeyboardButton();
        matches.setText(locale.getMessage("discover-main-matches"));
        matches.setCallbackData("matches");
        InlineKeyboardButton back = new InlineKeyboardButton();
        back.setText(locale.getMessage("menu-back"));
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
