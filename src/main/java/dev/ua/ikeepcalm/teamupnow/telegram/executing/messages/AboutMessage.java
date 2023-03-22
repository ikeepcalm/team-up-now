package dev.ua.ikeepcalm.teamupnow.telegram.executing.messages;

import dev.ua.ikeepcalm.teamupnow.aop.annotations.Progressable;
import dev.ua.ikeepcalm.teamupnow.database.dao.service.impls.CredentialsService;
import dev.ua.ikeepcalm.teamupnow.database.entities.Credentials;
import dev.ua.ikeepcalm.teamupnow.database.entities.Description;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.ProgressENUM;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.Executable;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.TelegramService;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.MultiMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class AboutMessage implements Executable {


    @Autowired
    private TelegramService telegramService;

    @Autowired
    private CredentialsService credentialsService;

    @Override
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
        multiMessage.setText("""
                Good news! You can now consider your profile complete, 
                just a little bit more and you will have access to the full functionality of the bot.
                To open the main menu, click on the button below the text input field one last time!
                                    """);
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
