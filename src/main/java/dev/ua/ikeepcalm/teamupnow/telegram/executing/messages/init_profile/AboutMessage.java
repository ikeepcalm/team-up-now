package dev.ua.ikeepcalm.teamupnow.telegram.executing.messages.init_profile;

import dev.ua.ikeepcalm.teamupnow.aop.annotations.Progressable;
import dev.ua.ikeepcalm.teamupnow.database.entities.Credentials;
import dev.ua.ikeepcalm.teamupnow.database.entities.Description;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.ProgressENUM;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.MultiMessage;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class AboutMessage extends dev.ua.ikeepcalm.teamupnow.telegram.executing.messages.Message {
    @Transactional
    @Progressable(ProgressENUM.ABOUT)
    public void execute(Message origin){
        ResourceBundle locale = getBundle(origin);
        Credentials credentials = credentialsService.findByAccountId(origin.getFrom().getId());
        if (credentials.getDescription() == null){
            Description description = new Description();
            description.setDescription(origin.getText());
            description.setCredentials(credentials);
            credentials.setDescription(description);
        } else {
            credentials.getDescription().setDescription(origin.getText());
        }
        credentials.getProgress().setProgressENUM(ProgressENUM.DONE);
        credentialsService.save(credentials);
        MultiMessage multiMessage = new MultiMessage();
        multiMessage.setText(locale.getString("profile-about"));
        multiMessage.setChatId(origin.getChatId());
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add("/menu");
        keyboardRows.add(row);
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        multiMessage.setReplyKeyboard(replyKeyboardMarkup);
        logger.info("(✔) User [@" + origin.getFrom().getUserName()+"] finished completing profile!");
        matchService.createMatchesForUser(credentials);
        telegramService.sendMultiMessage(multiMessage);
    }
}
