package dev.ua.ikeepcalm.teamupnow.telegram.executing.responses;

import dev.ua.ikeepcalm.teamupnow.aop.annotations.Progressable;
import dev.ua.ikeepcalm.teamupnow.database.dao.service.impls.CredentialsService;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.ProgressENUM;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.services.TelegramService;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.services.implementations.TelegramServiceExecutor;
import dev.ua.ikeepcalm.teamupnow.telegram.proxies.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class GamesResponse {

    @Autowired
    private TelegramService telegramService;

    @Progressable(ProgressENUM.GAMES)
    public void execute(Update update) {
        {
            Callback callback = new Callback();
            callback.setText("This is how you will interact with me in most cases. And now for the actual encounter:");
            callback.setChatId(update.getMessage().getChatId());
            ReplyKeyboardRemove remove = new ReplyKeyboardRemove();
            remove.setRemoveKeyboard(true);
            callback.setReplyKeyboard(remove);
            telegramService.sendCallback(callback);
        }
        Callback callback = new Callback();
        callback.setText("Select the games you play (you can choose more than one)." +
                " When you're done, click the green check mark to continue.");
        callback.setChatId(update.getMessage().getChatId());
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
        InlineKeyboardButton destiny2 = new InlineKeyboardButton("Destiny 2");
        destiny2.setText("Destiny 2");
        destiny2.setCallbackData("profile-games-destiny2");
        InlineKeyboardButton ready = new InlineKeyboardButton();
        ready.setText("Ready ✅");
        ready.setCallbackData("profile-games-ready");
        firstRow.add(minecraft);
        firstRow.add(csgo);
        firstRow.add(destiny2);
        secondRow.add(ready);
        keyboard.add(firstRow);
        keyboard.add(secondRow);
        inlineKeyboardMarkup.setKeyboard(keyboard);
        callback.setReplyKeyboard(inlineKeyboardMarkup);
        telegramService.sendCallback(callback);
    }
}
