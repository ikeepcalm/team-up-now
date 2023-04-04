package dev.ua.ikeepcalm.teamupnow.telegram.executing.commands;

import dev.ua.ikeepcalm.teamupnow.aop.annotations.EntryPoint;
import dev.ua.ikeepcalm.teamupnow.database.dao.service.impls.CredentialsService;
import dev.ua.ikeepcalm.teamupnow.database.entities.Credentials;
import dev.ua.ikeepcalm.teamupnow.database.entities.Progress;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.LanguageENUM;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.ProgressENUM;
import dev.ua.ikeepcalm.teamupnow.database.exceptions.DAOException;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.Executable;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.TelegramService;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.MultiMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class StartCommand implements Executable {

    @Autowired
    private CredentialsService credentialsService;
    @Autowired
    private TelegramService telegramService;

    @Override
    @EntryPoint
    public void execute(Message origin) {
        createObjectForNewUser(origin.getChatId(), origin.getFrom().getUserName(), origin.getFrom().getLanguageCode());
        MultiMessage multiMessage = new MultiMessage();
        multiMessage.setText("""
                Nice to meet you here!

                It's time to get acquainted so I know what kind of friends to find for you!

                To start, click the button that appears below the text entry field.""");
        multiMessage.setChatId(origin.getChatId());
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add("/games");
        keyboardRows.add(row);
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        multiMessage.setReplyKeyboard(replyKeyboardMarkup);
        telegramService.sendMultiMessage(multiMessage);
    }

    private void createObjectForNewUser(long userId, String username, String langCode){
        try {
            credentialsService.findByAccountId(userId);
        } catch (DAOException e){
            Progress progress = new Progress();
            progress.setProgressENUM(ProgressENUM.GAMES);
            Credentials credentials = new Credentials();
            credentials.setAccountId(userId);
            credentials.setUsername(username);
            credentials.setUiLanguage(determineLanguageCode(langCode));
            credentials.setProgress(progress);
            credentialsService.save(credentials);
        }
    }

    private LanguageENUM determineLanguageCode(String langCode){
        if (langCode.equals("uk")){
            return LanguageENUM.UKRAINIAN;
        } else {
            return LanguageENUM.ENGLISH;
        }
    }
}
