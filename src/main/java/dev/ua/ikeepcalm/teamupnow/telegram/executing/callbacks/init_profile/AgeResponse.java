package dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.init_profile;


import dev.ua.ikeepcalm.teamupnow.aop.annotations.I18N;
import dev.ua.ikeepcalm.teamupnow.database.entities.Credentials;
import dev.ua.ikeepcalm.teamupnow.database.entities.Demographic;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.AgeENUM;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.ProgressENUM;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.SimpleCallback;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.MultiMessage;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.PurgeMessage;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.tools.LocaleTool;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class AgeResponse extends SimpleCallback {

    private LocaleTool locale;

    @I18N
    @Override
    @Transactional
    public void manage(String receivedCallback, Message origin) {
        Credentials credentials = credentialsService.findByAccountId(origin.getChatId());
        Demographic demographic = null;
        try {
            switch (receivedCallback) {
                case "profile-age-category-young" -> {
                    demographic = new Demographic();
                    demographic.setAge(AgeENUM.YOUNG);
                }
                case "profile-age-category-young-adult" -> {
                    demographic = new Demographic();
                    demographic.setAge(AgeENUM.YOUND_ADULT);
                }
                case "profile-age-category-adult"-> {
                    demographic = new Demographic();
                    demographic.setAge(AgeENUM.ADULT);
                }
            }
        } finally {
            demographic.setCredentialsId(credentials);
            credentials.setDemographic(demographic);
            credentials.getProgress().setProgressENUM(ProgressENUM.ABOUT);
            credentialsService.save(credentials);
            telegramService.sendPurgeMessage(new PurgeMessage(origin.getMessageId(), origin.getChatId()));
            MultiMessage multiMessage = new MultiMessage();
            multiMessage.setText(locale.getMessage("age-response"));
            multiMessage.setChatId(origin.getChatId());
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
