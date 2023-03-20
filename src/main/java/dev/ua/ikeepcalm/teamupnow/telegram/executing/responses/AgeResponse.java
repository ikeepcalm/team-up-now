package dev.ua.ikeepcalm.teamupnow.telegram.executing.responses;

import dev.ua.ikeepcalm.teamupnow.aop.annotations.Progressable;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.ProgressENUM;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.services.TelegramService;
import dev.ua.ikeepcalm.teamupnow.telegram.proxies.Callback;
import dev.ua.ikeepcalm.teamupnow.telegram.proxies.ToDelete;
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
public class AgeResponse {

    @Autowired
    private TelegramService telegramService;

    @Progressable(ProgressENUM.AGE)
    public void execute(Update update) {
        {
            Callback callback = new Callback();
            callback.setText("Easter-egg hint: you can click on the field below the text entry" +
                    "area in order to move forward faster, than manually typing commands");
            callback.setChatId(update.getMessage().getChatId());
            ReplyKeyboardRemove remove = new ReplyKeyboardRemove();
            remove.setRemoveKeyboard(true);
            callback.setReplyKeyboard(remove);
            Message message =  telegramService.sendCallback(callback);
            ToDelete toDelete = new ToDelete(message.getMessageId(), message.getChatId());
            telegramService.deleteCallback(toDelete);
        }
        Callback callback = new Callback();
        callback.setText("Now, please, choose your age category (the system will try to match you with allies of similar age):");
        callback.setChatId(update.getMessage().getChatId());
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        InlineKeyboardButton youngCategory = new InlineKeyboardButton();
        youngCategory.setText("14-18");
        youngCategory.setCallbackData("profile-age-category-young");
        InlineKeyboardButton youndAdultCategory = new InlineKeyboardButton();
        youndAdultCategory.setText("18-26");
        youndAdultCategory.setCallbackData("profile-age-category-young-adult");
        InlineKeyboardButton adultCategory = new InlineKeyboardButton();
        adultCategory.setText("27-35");
        adultCategory.setCallbackData("profile-age-category-adult");
        firstRow.add(youngCategory);
        firstRow.add(youndAdultCategory);
        firstRow.add(adultCategory);
        keyboard.add(firstRow);
        inlineKeyboardMarkup.setKeyboard(keyboard);
        callback.setReplyKeyboard(inlineKeyboardMarkup);
        telegramService.sendCallback(callback);
    }
}
