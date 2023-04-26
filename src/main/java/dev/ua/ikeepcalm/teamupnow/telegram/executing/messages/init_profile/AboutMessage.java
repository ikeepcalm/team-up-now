package dev.ua.ikeepcalm.teamupnow.telegram.executing.messages.init_profile;

import dev.ua.ikeepcalm.teamupnow.aop.annotations.I18N;
import dev.ua.ikeepcalm.teamupnow.aop.annotations.Progressable;
import dev.ua.ikeepcalm.teamupnow.database.entities.Credentials;
import dev.ua.ikeepcalm.teamupnow.database.entities.Description;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.ProgressENUM;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.MultiMessage;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.tools.LocaleTool;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class AboutMessage extends dev.ua.ikeepcalm.teamupnow.telegram.executing.messages.Message {
    private LocaleTool locale;

    @I18N
    @Progressable(ProgressENUM.ABOUT)
    @Transactional
    public void execute(Message origin){
        Credentials credentials = credentialsService.findByAccountId(origin.getFrom().getId());
        Description description = new Description();
        description.setDescription(origin.getText());
        credentials.setDescription(description);
        credentials.getProgress().setProgressENUM(ProgressENUM.DONE);
        credentialsService.save(credentials);
        MultiMessage multiMessage = new MultiMessage();
        multiMessage.setText(locale.getMessage("profile-about"));
        multiMessage.setChatId(origin.getChatId());
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add("/menu");
        keyboardRows.add(row);
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        multiMessage.setReplyKeyboard(replyKeyboardMarkup);
        telegramService.sendMultiMessage(multiMessage);
    }
}
