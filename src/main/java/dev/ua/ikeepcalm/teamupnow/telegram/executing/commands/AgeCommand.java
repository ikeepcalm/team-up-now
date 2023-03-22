package dev.ua.ikeepcalm.teamupnow.telegram.executing.commands;

import dev.ua.ikeepcalm.teamupnow.aop.annotations.Progressable;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.ProgressENUM;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.Executable;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.TelegramService;
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
public class AgeCommand implements Executable {

    @Autowired
    private TelegramService telegramService;

    @Override
    @Progressable(ProgressENUM.AGE)
    public void execute(Message origin) {
        {
            MultiMessage multiMessage = new MultiMessage();
            multiMessage.setText("Easter-egg hint: you can click on the field below the text entry" +
                    "area in order to move forward faster, than manually typing commands");
            multiMessage.setChatId(origin.getChatId());
            ReplyKeyboardRemove remove = new ReplyKeyboardRemove();
            remove.setRemoveKeyboard(true);
            multiMessage.setReplyKeyboard(remove);
            Message message =  telegramService.sendMultiMessage(multiMessage);
            PurgeMessage purgeMessage = new PurgeMessage(message.getMessageId(), message.getChatId());
            telegramService.deleteMessage(purgeMessage);
        }
        MultiMessage multiMessage = new MultiMessage();
        multiMessage.setText("Now, please, choose your age category (the system will try to match you with allies of similar age):");
        multiMessage.setChatId(origin.getChatId());
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
        multiMessage.setReplyKeyboard(inlineKeyboardMarkup);
        telegramService.sendMultiMessage(multiMessage);
    }
}
