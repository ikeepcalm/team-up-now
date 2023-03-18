package dev.ua.ikeepcalm.teamupnow.telegram.executing.services;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

public interface CallbackService {
    ReplyKeyboard createInlineKeyboardMarkup(List<InlineKeyboardButton> theOnlyRow);
    ReplyKeyboard createInlineKeyboardMarkup(List<InlineKeyboardButton> firstRow, List<InlineKeyboardButton> secondRow);
}
