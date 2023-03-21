package dev.ua.ikeepcalm.teamupnow.telegram.executing.responses;

import dev.ua.ikeepcalm.teamupnow.aop.annotations.Progressable;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.ProgressENUM;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.services.TelegramService;
import dev.ua.ikeepcalm.teamupnow.telegram.proxies.MediaMessage;
import dev.ua.ikeepcalm.teamupnow.telegram.proxies.MultiMessage;
import dev.ua.ikeepcalm.teamupnow.telegram.proxies.PurgeMessage;
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
public class MenuResponse {

    @Autowired
    private TelegramService telegramService;

    @Progressable(ProgressENUM.DONE)
    public void execute(Update update) {
        long chatId = update.getMessage().getChatId();
        {
            MultiMessage multiMessage = new MultiMessage();
            multiMessage.setText("Easter-egg fact: there are 108 cards in the Uno game");
            multiMessage.setChatId(chatId);
            ReplyKeyboardRemove remove = new ReplyKeyboardRemove();
            remove.setRemoveKeyboard(true);
            multiMessage.setReplyKeyboard(remove);
            Message message = telegramService.sendMultiMessage(multiMessage);
            PurgeMessage purgeMessage = new PurgeMessage(message.getMessageId(), message.getChatId());
            telegramService.deleteMessage(purgeMessage);
        }
        MediaMessage message = new MediaMessage();
        message.setFilePath("src/main/resources/menu.png");
        message.setChatId(chatId);
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
