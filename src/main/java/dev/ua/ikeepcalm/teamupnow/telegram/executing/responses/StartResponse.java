package dev.ua.ikeepcalm.teamupnow.telegram.executing.responses;

import dev.ua.ikeepcalm.teamupnow.database.entities.Credentials;
import dev.ua.ikeepcalm.teamupnow.database.entities.Progress;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.LanguageENUM;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.ProgressENUM;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.services.TelegramService;
import dev.ua.ikeepcalm.teamupnow.telegram.proxies.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class StartResponse {
//    @Autowired
//    private CredentialsService credentialsService;
    @Autowired
    private TelegramService telegramService;

    public void execute(Update update) {
        createObjectForNewUser(update);
        Callback callback = new Callback();
        callback.setText("""
                Nice to meet you here!

                It's time to get acquainted so I know what kind of friends to find for you!

                To start, click the button that appears below the text entry field.""");
        callback.setChatId(update.getMessage().getChatId());
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add("/profile");
        keyboardRows.add(row);
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        callback.setReplyKeyboard(replyKeyboardMarkup);
        telegramService.sendCallback(callback);
    }

    private void createObjectForNewUser(Update update){
        Progress progress = new Progress();
        progress.setProgressENUM(ProgressENUM.GAMES);
        Credentials credentials = new Credentials();
        credentials.setAccountId(update.getMessage().getFrom().getId());
        credentials.setUsername(update.getMessage().getFrom().getUserName());
        credentials.setUiLanguage(determineLanguageCode(update));
        credentials.setProgress(progress);
//        credentialsService.save(credentials);
    }

    private LanguageENUM determineLanguageCode(Update update){
        if (update.getMessage().getFrom().getLanguageCode().equals("uk")){
            return LanguageENUM.UKRAINIAN;
        } else {
            return LanguageENUM.ENGLISH;
        }
    }
}
