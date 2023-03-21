package dev.ua.ikeepcalm.teamupnow.telegram.handling.subhandlers;


import dev.ua.ikeepcalm.teamupnow.database.dao.service.impls.CredentialsService;
import dev.ua.ikeepcalm.teamupnow.database.entities.Credentials;
import dev.ua.ikeepcalm.teamupnow.database.entities.Demographic;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.AgeENUM;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.ProgressENUM;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.services.TelegramService;
import dev.ua.ikeepcalm.teamupnow.telegram.proxies.MultiMessage;
import dev.ua.ikeepcalm.teamupnow.telegram.proxies.PurgeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class AgeSubHandler implements SubHandler {

    @Autowired
    private TelegramService telegramService;

    @Autowired
    private CredentialsService credentialsService;

    @Override
    @Transactional
    public void manage(Update update) {
        String currentCallback = update.getCallbackQuery().getData();
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        int messageId = update.getCallbackQuery().getMessage().getMessageId();
        Credentials credentials = credentialsService.findByAccountId(update.getCallbackQuery().getFrom().getId());
        try {
            switch (currentCallback) {
                case "profile-age-category-young" -> {
                    Demographic demographic = new Demographic();
                    demographic.setAge(AgeENUM.YOUNG);
                    credentials.setDemographic(demographic);
                }
                case "profile-age-category-young-adult" -> {
                    Demographic demographic = new Demographic();
                    demographic.setAge(AgeENUM.YOUND_ADULT);
                    credentials.setDemographic(demographic);
                }
                case "profile-age-category-adult"-> {
                    Demographic demographic = new Demographic();
                    demographic.setAge(AgeENUM.ADULT);
                    credentials.setDemographic(demographic);
                }
            }
        } finally {
            credentials.getProgress().setProgressENUM(ProgressENUM.ABOUT);
            credentialsService.save(credentials);
            telegramService.deleteMessage(new PurgeMessage(messageId,chatId));
            MultiMessage multiMessage = new MultiMessage();
            multiMessage.setText("""
                    Got it.
                    
                    Let's not waste time, hop on the the next step.
                    """);
            multiMessage.setChatId(update.getCallbackQuery().getMessage().getChatId());
            ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
            List<KeyboardRow> keyboardRows = new ArrayList<>();
            KeyboardRow row = new KeyboardRow();
            row.add("/about");
            keyboardRows.add(row);
            replyKeyboardMarkup.setKeyboard(keyboardRows);
            multiMessage.setReplyKeyboard(replyKeyboardMarkup);
            telegramService.sendMultiMessage(multiMessage);
        }
    }
}
