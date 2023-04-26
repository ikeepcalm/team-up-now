package dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.profile;

import dev.ua.ikeepcalm.teamupnow.aop.annotations.I18N;
import dev.ua.ikeepcalm.teamupnow.aop.annotations.Sequenced;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.SimpleCallback;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.tools.LocaleTool;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.MultiMessage;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class EditProfileResponse extends SimpleCallback {
    private LocaleTool locale;

    @I18N
    @Override
    @Sequenced
    public void manage(String receivedCallback, Message origin) {
        credentialsService.deleteCredentials(origin.getChatId());
        MultiMessage multiMessage = new MultiMessage();
        multiMessage.setText(locale.getMessage("edit-profile-response"));
        multiMessage.setChatId(origin.getChatId());
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add("/start");
        keyboardRows.add(row);
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        multiMessage.setReplyKeyboard(replyKeyboardMarkup);
        telegramService.sendMultiMessage(multiMessage);
    }
}
