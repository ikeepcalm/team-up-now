package dev.ua.ikeepcalm.teamupnow.telegram.executing.commands;

import dev.ua.ikeepcalm.teamupnow.aop.annotations.Progressable;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.ProgressENUM;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.Executable;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.TelegramService;
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

    @Override
    @Progressable(ProgressENUM.GAMES)
    public void execute(Message origin) {
        {
            MultiMessage multiMessage = new MultiMessage();
            multiMessage.setText("This is how you will interact with me in most cases. And now for the actual encounter:");
            multiMessage.setChatId(origin.getChatId());
            ReplyKeyboardRemove remove = new ReplyKeyboardRemove();
            remove.setRemoveKeyboard(true);
            multiMessage.setReplyKeyboard(remove);
            telegramService.sendMultiMessage(multiMessage);
        }
        MultiMessage multiMessage = new MultiMessage();
        multiMessage.setText("Select the games you play (you can choose more than one)." +
                " When you're done, click the green check mark to continue.");
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
        ready.setText("Ready ✅");
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
