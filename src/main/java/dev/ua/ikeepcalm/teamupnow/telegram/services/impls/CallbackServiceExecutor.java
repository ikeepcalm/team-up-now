package dev.ua.ikeepcalm.teamupnow.telegram.services.impls;

import dev.ua.ikeepcalm.teamupnow.telegram.services.CallbackService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Service
public class CallbackServiceExecutor implements CallbackService {
    @Override
    public ReplyKeyboard createInlineKeyboardMarkup(List<InlineKeyboardButton> theOnlyRow) {
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(theOnlyRow);
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        keyboardMarkup.setKeyboard(keyboard);
        return keyboardMarkup;
    }

    @Override
    public ReplyKeyboard createInlineKeyboardMarkup(List<InlineKeyboardButton> firstRow, List<InlineKeyboardButton> secondRow) {
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(firstRow); keyboard.add(secondRow);
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        keyboardMarkup.setKeyboard(keyboard);
        return keyboardMarkup;
    }
}
