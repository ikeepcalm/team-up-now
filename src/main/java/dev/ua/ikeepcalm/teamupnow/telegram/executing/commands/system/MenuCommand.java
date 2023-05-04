package dev.ua.ikeepcalm.teamupnow.telegram.executing.commands.system;

import dev.ua.ikeepcalm.teamupnow.aop.annotations.ClearKeyboard;
import dev.ua.ikeepcalm.teamupnow.aop.annotations.I18N;
import dev.ua.ikeepcalm.teamupnow.aop.annotations.Progressable;

import dev.ua.ikeepcalm.teamupnow.database.entities.source.ProgressENUM;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.commands.Command;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.tools.LocaleTool;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.MultiMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class MenuCommand extends Command {

    @Value("${img.menu}")
    String filePath;
    private LocaleTool locale;

    @I18N
    @ClearKeyboard
    @Progressable(ProgressENUM.DONE)
    public void execute(Message origin) {
        MultiMessage message = new MultiMessage();
        message.setFilePath(filePath);
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
        telegramService.sendMultiMessage(message);
    }
}
