package dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.init_profile;

import dev.ua.ikeepcalm.teamupnow.aop.annotations.I18N;
import dev.ua.ikeepcalm.teamupnow.database.entities.Credentials;
import dev.ua.ikeepcalm.teamupnow.database.entities.Game;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.GameENUM;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.ProgressENUM;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.SimpleCallback;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.AlterMessage;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.MultiMessage;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.PurgeMessage;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.tools.LocaleTool;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope(value = "prototype")
public class GamesResponse extends SimpleCallback {

    private LocaleTool locale;
    private final List<String> selectedGamesCallbacks = new ArrayList<>();
    private int page = 1;


    private void manageGamesButton(String receivedCallback, Message origin) {
        InlineKeyboardMarkup keyboardMarkup = origin.getReplyMarkup();
        List<List<InlineKeyboardButton>> keyboard = keyboardMarkup.getKeyboard();
        List<List<InlineKeyboardButton>> keyboardCopy = new ArrayList<>(keyboard); // make a copy of the keyboard
        for (List<InlineKeyboardButton> row : keyboardCopy) {
            List<InlineKeyboardButton> rowCopy = new ArrayList<>(row); // make a copy of the row
            for (InlineKeyboardButton button : rowCopy) {
                if (button.getCallbackData().equals(receivedCallback)) {
                    if (selectedGamesCallbacks.contains(button.getCallbackData())) {
                        selectedGamesCallbacks.remove(button.getCallbackData());
                        editMessage(origin);
                    } else {
                        selectedGamesCallbacks.add(button.getCallbackData());
                        editMessage(origin);
                    }
                }
            }
        }
    }


    private void managePaginationButton(String receivedCallback, Message origin) {
        if (receivedCallback.equals("profile-games-next")) {
            ++page;
            editMessage(origin);
        } else if (receivedCallback.equals("profile-games-back")) {
            if (page > 1) {
                --page;
                editMessage(origin);
            }
        }
    }

    private void editMessage(Message origin) {
        AlterMessage alterMessage = new AlterMessage();
        alterMessage.setMessageId(origin.getMessageId());
        alterMessage.setChatId(origin.getChatId());
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        int start = (page - 1) * 6; // calculate the starting index of games for this page
        int end = Math.min(start + 6, GameENUM.values().length); // calculate the ending index of games for this page

        for (int i = start; i < end; i += 2) {
            List<InlineKeyboardButton> row = new ArrayList<>();
            InlineKeyboardButton button = new InlineKeyboardButton();
            String callbackData = GameENUM.values()[i].getButtonCallback();
            button.setText(selectedGamesCallbacks.contains(callbackData) ? GameENUM.values()[i].getButtonText() + " ✓" : GameENUM.values()[i].getButtonText());
            button.setCallbackData(callbackData);
            row.add(button);
            if (i + 1 < end) {
                InlineKeyboardButton button1 = new InlineKeyboardButton();
                callbackData = "profile-games-" + GameENUM.values()[i + 1];
                button1.setText(selectedGamesCallbacks.contains(callbackData) ? GameENUM.values()[i + 1].getButtonText() + " ✓" : GameENUM.values()[i + 1].getButtonText());
                button1.setCallbackData(callbackData);
                row.add(button1);
            }
            rows.add(row);
        }

        List<InlineKeyboardButton> paginationRow = new ArrayList<>();
        if (page > 1) {
            InlineKeyboardButton backButton = new InlineKeyboardButton();
            backButton.setText(locale.getMessage("explore-previous"));
            backButton.setCallbackData("profile-games-back");
            paginationRow.add(backButton);
        }
        if (end < GameENUM.values().length) {
            InlineKeyboardButton backButton = new InlineKeyboardButton();
            backButton.setText(locale.getMessage("explore-next"));
            backButton.setCallbackData("profile-games-next");
            paginationRow.add(backButton);
        }
        rows.add(paginationRow);

        List<InlineKeyboardButton> readyRow = new ArrayList<>();
        InlineKeyboardButton ready = new InlineKeyboardButton();
        ready.setText(locale.getMessage("profile-ready"));
        ready.setCallbackData("profile-games-ready");
        readyRow.add(ready);
        rows.add(readyRow);

        inlineKeyboardMarkup.setKeyboard(rows);
        alterMessage.setReplyKeyboard(inlineKeyboardMarkup);
        telegramService.sendAlterMessage(alterMessage);
    }


    @I18N
    @Override
    @Transactional
    public void manage(String receivedCallback, Message origin) {
        if (receivedCallback.equals("profile-games-ready")) {
            if (selectedGamesCallbacks.isEmpty()) {
                MultiMessage multiMessage = new MultiMessage();
                multiMessage.setChatId(origin.getChatId());
                multiMessage.setText(locale.getMessage("games-error-response"));
                ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
                List<KeyboardRow> keyboardRows = new ArrayList<>();
                KeyboardRow row = new KeyboardRow();
                row.add("/games");
                keyboardRows.add(row);
                replyKeyboardMarkup.setKeyboard(keyboardRows);
                multiMessage.setReplyKeyboard(replyKeyboardMarkup);
                telegramService.sendPurgeMessage(new PurgeMessage(origin.getMessageId(), origin.getChatId()));
                telegramService.sendMultiMessage(multiMessage);
            } else {
                Credentials credentials = credentialsService.findByAccountId(origin.getChatId());
                for (String callback : selectedGamesCallbacks) {
                    for (GameENUM gameENUM : GameENUM.values()) {
                        if (callback.equals(gameENUM.getButtonCallback())) {
                            Game game = new Game();
                            game.setName(gameENUM);
                            game.setCredentials(credentials);
                            credentials.getGames().add(game);
                        }
                    }
                }
                credentials.getProgress().setProgressENUM(ProgressENUM.AGE);
                credentialsService.save(credentials);
                MultiMessage multiMessage = new MultiMessage();
                multiMessage.setText(locale.getMessage("games-success-response"));
                multiMessage.setChatId(origin.getChatId());
                ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
                List<KeyboardRow> keyboardRows = new ArrayList<>();
                KeyboardRow row = new KeyboardRow();
                row.add("/age");
                keyboardRows.add(row);
                replyKeyboardMarkup.setKeyboard(keyboardRows);
                multiMessage.setReplyKeyboard(replyKeyboardMarkup);
                telegramService.sendPurgeMessage(new PurgeMessage(origin.getMessageId(), origin.getChatId()));
                telegramService.sendMultiMessage(multiMessage);
            }
        } else if (receivedCallback.equals("profile-games-next") || receivedCallback.equals("profile-games-back")) {
            managePaginationButton(receivedCallback, origin);
        } else if (receivedCallback.startsWith("profile-games")){
            manageGamesButton(receivedCallback, origin);
        }
    }
}
