package dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.menu;

import dev.ua.ikeepcalm.teamupnow.aop.annotations.I18N;
import dev.ua.ikeepcalm.teamupnow.aop.annotations.Sequenced;
import dev.ua.ikeepcalm.teamupnow.database.dao.service.impls.CredentialsService;
import dev.ua.ikeepcalm.teamupnow.database.entities.Credentials;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.ProgressENUM;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.Executable;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.TelegramService;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.implementations.LocaleTool;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.MultiMessage;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.PurgeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class EditProfileResponse implements Executable {

    @Autowired
    private TelegramService telegramService;
    @Autowired
    private CredentialsService credentialsService;
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
