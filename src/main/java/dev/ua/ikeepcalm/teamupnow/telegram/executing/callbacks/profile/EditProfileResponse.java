package dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.profile;

import dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.SimpleCallback;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.MultiMessage;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.PurgeMessage;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class EditProfileResponse extends SimpleCallback {
    @Override
    public void manage(String receivedCallback, CallbackQuery origin) {
        ResourceBundle locale = getBundle(origin);
        PurgeMessage purgeMessage = new PurgeMessage(origin.getMessage().getMessageId(), origin.getMessage().getChatId());
        MultiMessage multiMessage = new MultiMessage();
        multiMessage.setText(locale.getString("edit-profile-response"));
        multiMessage.setChatId(origin.getMessage().getChatId());
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        List<InlineKeyboardButton> secondRow = new ArrayList<>();

        InlineKeyboardButton editGames = new InlineKeyboardButton();
        editGames.setText(locale.getString("edit-profile-games"));
        editGames.setCallbackData("edit-profile-games");

        InlineKeyboardButton editAge = new InlineKeyboardButton();
        editAge.setText(locale.getString("edit-profile-age"));
        editAge.setCallbackData("edit-profile-age");

        InlineKeyboardButton editDescription = new InlineKeyboardButton();
        editDescription.setText(locale.getString("edit-profile-description"));
        editDescription.setCallbackData("edit-profile-about");

        InlineKeyboardButton back = new InlineKeyboardButton();
        back.setText(locale.getString("edit-profile-back"));
        back.setCallbackData("edit-profile-back");

        firstRow.add(editGames);
        firstRow.add(editAge);
        firstRow.add(editDescription);
        secondRow.add(back);
        keyboard.add(firstRow);
        keyboard.add(secondRow);
        inlineKeyboardMarkup.setKeyboard(keyboard);
        multiMessage.setReplyKeyboard(inlineKeyboardMarkup);
        telegramService.sendPurgeMessage(purgeMessage);
        telegramService.sendMultiMessage(multiMessage);
    }
}
