package dev.ua.ikeepcalm.teamupnow.telegram.executing.commands.system;

import dev.ua.ikeepcalm.teamupnow.aop.annotations.EntryPoint;
import dev.ua.ikeepcalm.teamupnow.database.entities.Credentials;
import dev.ua.ikeepcalm.teamupnow.database.entities.Progress;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.LanguageENUM;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.ProgressENUM;
import dev.ua.ikeepcalm.teamupnow.database.exceptions.DAOException;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.commands.Command;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.MultiMessage;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class StartCommand extends Command {

    private ResourceBundle locale;

    @EntryPoint
    public void execute(Message origin) {
        locale = getBundle(origin);
        createObjectForNewUser(origin.getChatId(), origin.getFrom().getUserName(), origin.getFrom().getLanguageCode(), origin.getFrom().getFirstName());
        MultiMessage multiMessage = new MultiMessage();
        multiMessage.setText(locale.getString("start-message"));
        multiMessage.setChatId(origin.getChatId());
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add("/games");
        keyboardRows.add(row);
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        multiMessage.setReplyKeyboard(replyKeyboardMarkup);
        telegramService.sendMultiMessage(multiMessage);
    }

    private void createObjectForNewUser(long userId, String username, String langCode, String firstName){
        try {
            credentialsService.findByAccountId(userId);
        } catch (DAOException e){
            if (username == null){
                MultiMessage message = new MultiMessage();
                message.setChatId(userId);
                message.setText(locale.getString("no-username"));
                telegramService.sendMultiMessage(message);
            } else{
                Credentials credentials = new Credentials();
                credentials.setAccountId(userId);
                credentials.setUsername(username);
                credentials.setName(firstName);
                credentials.setUiLanguage(determineLanguageCode(langCode));
                credentials.setConnectionTokens(5);
                credentials.setSustainableTokens(5);
                Progress progress = new Progress();
                progress.setProgressENUM(ProgressENUM.GAMES);
                progress.setCredentials(credentials);
                credentials.setProgress(progress);
                credentialsService.save(credentials);
            }
        }
    }

    private LanguageENUM determineLanguageCode(String langCode){
        if (langCode.equals("uk") || langCode.equals("ru")){
            return LanguageENUM.UKRAINIAN;
        } else {
            return LanguageENUM.ENGLISH;
        }
    }
}
