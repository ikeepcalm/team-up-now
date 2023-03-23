package dev.ua.ikeepcalm.teamupnow.telegram.executing.commands;

import dev.ua.ikeepcalm.teamupnow.aop.annotations.ClearKeyboard;
import dev.ua.ikeepcalm.teamupnow.aop.annotations.Progressable;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.ProgressENUM;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.Executable;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.TelegramService;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.MediaMessage;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.MultiMessage;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.PurgeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class MenuCommand implements Executable {

    @Autowired
    private TelegramService telegramService;

    @Override
    @ClearKeyboard
    @Progressable(ProgressENUM.DONE)
    public void execute(Message origin) {
        MediaMessage message = new MediaMessage();
        message.setFilePath("src/main/resources/menu.png");
        message.setChatId(origin.getChatId());
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        List<InlineKeyboardButton> secondRow = new ArrayList<>();
        InlineKeyboardButton profile = new InlineKeyboardButton();
        profile.setText("Profile \uD83D\uDC64");
        profile.setCallbackData("menu-profile");
        InlineKeyboardButton discover = new InlineKeyboardButton();
        discover.setText("Discover \uD83D\uDD0D");
        discover.setCallbackData("menu-discover");
        InlineKeyboardButton settings = new InlineKeyboardButton();
        settings.setText("Settings ⚙️");
        settings.setCallbackData("menu-settings");
        InlineKeyboardButton more = new InlineKeyboardButton();
        more.setText("More \uD83D\uDCC8");
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
