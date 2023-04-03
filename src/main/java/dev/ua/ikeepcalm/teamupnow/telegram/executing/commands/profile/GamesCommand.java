package dev.ua.ikeepcalm.teamupnow.telegram.executing.commands.profile;

import dev.ua.ikeepcalm.teamupnow.aop.annotations.ClearKeyboard;
import dev.ua.ikeepcalm.teamupnow.aop.annotations.I18N;
import dev.ua.ikeepcalm.teamupnow.aop.annotations.Progressable;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.ProgressENUM;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.Executable;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.TelegramService;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.implementations.LocaleTool;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.MultiMessage;
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
public class GamesCommand implements Executable {

    @Autowired
    private TelegramService telegramService;
    private LocaleTool locale;

    @I18N
    @Override
    @ClearKeyboard
    @Progressable(ProgressENUM.GAMES)
    public void execute(Message origin) {
        MultiMessage multiMessage = new MultiMessage();
        multiMessage.setText(locale.getMessage("profile-games"));
        multiMessage.setChatId(origin.getChatId());
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        List<InlineKeyboardButton> secondRow = new ArrayList<>();
        InlineKeyboardButton minecraft = new InlineKeyboardButton();
        minecraft.setText("Minecraft");
        minecraft.setCallbackData("profile-games-minecraft");
        InlineKeyboardButton csgo = new InlineKeyboardButton();
        csgo.setText("CS:GO");
        csgo.setCallbackData("profile-games-csgo");
        InlineKeyboardButton destiny2 = new InlineKeyboardButton();
        destiny2.setText("Destiny 2");
        destiny2.setCallbackData("profile-games-destiny2");
        InlineKeyboardButton ready = new InlineKeyboardButton();
        ready.setText(locale.getMessage("profile-ready"));
        ready.setCallbackData("profile-games-ready");
        firstRow.add(minecraft);
        firstRow.add(csgo);
        firstRow.add(destiny2);
        secondRow.add(ready);
        keyboard.add(firstRow);
        keyboard.add(secondRow);
        inlineKeyboardMarkup.setKeyboard(keyboard);
        multiMessage.setReplyKeyboard(inlineKeyboardMarkup);
        telegramService.sendMultiMessage(multiMessage);
    }
}
