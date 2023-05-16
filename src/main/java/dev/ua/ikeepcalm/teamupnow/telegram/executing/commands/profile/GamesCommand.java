package dev.ua.ikeepcalm.teamupnow.telegram.executing.commands.profile;

import dev.ua.ikeepcalm.teamupnow.aop.annotations.ClearKeyboard;
import dev.ua.ikeepcalm.teamupnow.aop.annotations.I18N;
import dev.ua.ikeepcalm.teamupnow.aop.annotations.Progressable;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.GameENUM;
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
public class GamesCommand extends Command {
    private LocaleTool locale;

    @I18N
    @ClearKeyboard
    @Progressable(ProgressENUM.GAMES)
    public void execute(Message origin) {
        MultiMessage multiMessage = new MultiMessage();
        multiMessage.setText(locale.getMessage("profile-games"));
        multiMessage.setChatId(origin.getChatId());
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        GameENUM[] gameValues = GameENUM.values();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();
        for (int i = 0; i < gameValues.length; i++) {
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(gameValues[i].getButtonText());
            button.setCallbackData(gameValues[i].getButtonCallback());
            row.add(button);
            if (row.size() == 2 || i == gameValues.length - 1) {
                if (keyboard.size() == 4 &&
                    keyboard.get(0).size() == 2 &&
                    keyboard.get(1).size() == 2 &&
                    keyboard.get(2).size() == 2){
                } else {
                    keyboard.add(row);
                    row = new ArrayList<>();
                    if (keyboard.size() == 3 && i != gameValues.length - 1) {
                        InlineKeyboardButton nextButton = new InlineKeyboardButton();
                        nextButton.setText(locale.getMessage("explore-next"));
                        nextButton.setCallbackData("profile-games-next");
                        List<InlineKeyboardButton> paginationRow = new ArrayList<>();
                        paginationRow.add(nextButton);
                        keyboard.add(paginationRow);
                    }
                }
            }
        }

        List<InlineKeyboardButton> readyRow = new ArrayList<>();
        InlineKeyboardButton ready = new InlineKeyboardButton();
        ready.setText(locale.getMessage("profile-ready"));
        ready.setCallbackData("profile-games-ready");
        readyRow.add(ready);
        keyboard.add(readyRow);
        inlineKeyboardMarkup.setKeyboard(keyboard);
        multiMessage.setReplyKeyboard(inlineKeyboardMarkup);
        telegramService.sendMultiMessage(multiMessage);
    }
}
