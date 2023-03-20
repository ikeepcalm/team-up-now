package dev.ua.ikeepcalm.teamupnow.telegram.handling.subhandlers.implementation;

import dev.ua.ikeepcalm.teamupnow.aop.annotations.Progressable;
import dev.ua.ikeepcalm.teamupnow.database.dao.service.impls.CredentialsService;
import dev.ua.ikeepcalm.teamupnow.database.entities.Credentials;
import dev.ua.ikeepcalm.teamupnow.database.entities.Description;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.ProgressENUM;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.services.TelegramService;
import dev.ua.ikeepcalm.teamupnow.telegram.handling.subhandlers.SubHandler;
import dev.ua.ikeepcalm.teamupnow.telegram.proxies.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class AboutSubHandler implements SubHandler {


    @Autowired
    private TelegramService telegramService;

    @Autowired
    private CredentialsService credentialsService;

    @Override
    @Progressable(ProgressENUM.ABOUT)
    @Transactional
    public void manage(Update update) {
        String text = update.getMessage().getText();
        long accountId = update.getMessage().getFrom().getId();
        Credentials credentials = credentialsService.findByAccountId(accountId);
        Description description = new Description();
        description.setDescription(text);
        credentials.setDescription(description);
        credentials.getProgress().setProgressENUM(ProgressENUM.DONE);
        credentialsService.save(credentials);
        Callback callback = new Callback();
        callback.setText("""
                Good news! You can now consider your profile complete, 
                just a little bit more and you will have access to the full functionality of the bot.
                To open the main menu, click on the button below the text input field one last time!
                                    """);
        callback.setChatId(update.getMessage().getChatId());
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add("/menu");
        keyboardRows.add(row);
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        callback.setReplyKeyboard(replyKeyboardMarkup);
        telegramService.sendCallback(callback);
    }
}
