package dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.init_profile;


import dev.ua.ikeepcalm.teamupnow.database.entities.Credentials;
import dev.ua.ikeepcalm.teamupnow.database.entities.Demographic;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.AgeENUM;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.ProgressENUM;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.SimpleCallback;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.MultiMessage;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.PurgeMessage;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class AgeResponse extends SimpleCallback {

    @Override
    @Transactional
    public void manage(String receivedCallback, CallbackQuery origin) {
        ResourceBundle locale = getBundle(origin);
        Credentials credentials = credentialsService.findByAccountId(origin.getMessage().getChatId());
        Demographic demographic = null;
        try {
            switch (receivedCallback) {
                case "profile-age-category-young" -> {
                    demographic = new Demographic();
                    demographic.setAge(AgeENUM.YOUNG);
                }
                case "profile-age-category-young-adult" -> {
                    demographic = new Demographic();
                    demographic.setAge(AgeENUM.YOUNG_ADULT);
                }
                case "profile-age-category-adult"-> {
                    demographic = new Demographic();
                    demographic.setAge(AgeENUM.ADULT);
                }
            }
        } finally {
            assert demographic != null;
            demographic.setCredentials(credentials);
            credentials.setDemographic(demographic);
            credentials.getProgress().setProgressENUM(ProgressENUM.ABOUT);
            credentialsService.save(credentials);
            telegramService.sendPurgeMessage(new PurgeMessage(origin.getMessage().getMessageId(), origin.getMessage().getChatId()));
            MultiMessage multiMessage = new MultiMessage();
            multiMessage.setText(locale.getString("age-response"));
            multiMessage.setChatId(origin.getMessage().getChatId());
            ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
            List<KeyboardRow> keyboardRows = new ArrayList<>();
            KeyboardRow row = new KeyboardRow();
            row.add("/about");
            keyboardRows.add(row);
            replyKeyboardMarkup.setKeyboard(keyboardRows);
            replyKeyboardMarkup.setResizeKeyboard(true);
            replyKeyboardMarkup.setOneTimeKeyboard(true);
            multiMessage.setReplyKeyboard(replyKeyboardMarkup);
            telegramService.sendMultiMessage(multiMessage);
        }
    }
}
