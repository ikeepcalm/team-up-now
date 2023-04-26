package dev.ua.ikeepcalm.teamupnow.telegram.executing.commands.profile;

import dev.ua.ikeepcalm.teamupnow.aop.annotations.ClearKeyboard;
import dev.ua.ikeepcalm.teamupnow.aop.annotations.I18N;
import dev.ua.ikeepcalm.teamupnow.aop.annotations.Progressable;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.ProgressENUM;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.commands.Command;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.MultiMessage;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.tools.LocaleTool;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class AgeCommand extends Command {
    private LocaleTool locale;
    @I18N
    @Override
    @ClearKeyboard
    @Progressable(ProgressENUM.AGE)
    public void execute(Message origin) {
        MultiMessage multiMessage = new MultiMessage();
        multiMessage.setText(locale.getMessage("profile-age"));
        multiMessage.setChatId(origin.getChatId());
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        InlineKeyboardButton youngCategory = new InlineKeyboardButton();
        youngCategory.setText("14-17");
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
