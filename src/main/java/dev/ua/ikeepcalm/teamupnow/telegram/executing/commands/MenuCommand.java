package dev.ua.ikeepcalm.teamupnow.telegram.executing.commands;

import dev.ua.ikeepcalm.teamupnow.aop.annotations.ClearKeyboard;
import dev.ua.ikeepcalm.teamupnow.aop.annotations.I18N;
import dev.ua.ikeepcalm.teamupnow.aop.annotations.Progressable;

import dev.ua.ikeepcalm.teamupnow.database.entities.source.ProgressENUM;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.Executable;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.TelegramService;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.implementations.LocaleTool;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.MediaMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class MenuCommand implements Executable {

    @Autowired
    private TelegramService telegramService;
    private LocaleTool locale;

    @I18N
    @Override
    @ClearKeyboard
    @Progressable(ProgressENUM.DONE)
    public void execute(Message origin) {
        MediaMessage message = new MediaMessage();
        message.setFilePath("src/main/resources/img/menu.png");
        message.setChatId(origin.getChatId());
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        List<InlineKeyboardButton> secondRow = new ArrayList<>();
        InlineKeyboardButton profile = new InlineKeyboardButton();
        profile.setText(locale.getMessage("menu-profile"));
        profile.setCallbackData("menu-profile");
        InlineKeyboardButton discover = new InlineKeyboardButton();
        discover.setText(locale.getMessage("menu-discover"));
        discover.setCallbackData("menu-discover");
        InlineKeyboardButton settings = new InlineKeyboardButton();
        settings.setText(locale.getMessage("menu-settings"));
        settings.setCallbackData("menu-settings");
        InlineKeyboardButton more = new InlineKeyboardButton();
        more.setText(locale.getMessage("menu-more"));
        more.setCallbackData("menu-more");
        firstRow.add(profile);
        firstRow.add(discover);
        secondRow.add(settings);
        secondRow.add(more);
        keyboard.add(firstRow);
        keyboard.add(secondRow);
        inlineKeyboardMarkup.setKeyboard(keyboard);
        message.setReplyKeyboard(inlineKeyboardMarkup);
        telegramService.sendMediaMessage(message);
    }
}
