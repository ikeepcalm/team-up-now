package dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks;

import dev.ua.ikeepcalm.teamupnow.database.dao.service.impls.CredentialsService;
import dev.ua.ikeepcalm.teamupnow.database.entities.Credentials;
import dev.ua.ikeepcalm.teamupnow.database.entities.Game;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.GameENUM;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.ProgressENUM;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.Executable;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.TelegramService;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.AlterMessage;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.MultiMessage;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.PurgeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class GamesResponse implements Executable {

    @Autowired
    private TelegramService telegramService;

    @Autowired
    private CredentialsService credentialsService;

    @Transactional
    public void manage(String receivedCallback, Message origin) {
        Credentials credentials = credentialsService.findByAccountId(origin.getChatId());
        InlineKeyboardMarkup markup = origin.getReplyMarkup();
        if (!receivedCallback.equals("profile-games-ready")) {
            try {
                if (receivedCallback.startsWith("profile-games-minecraft")) {
                    updateButton("profile-games-minecraft", markup, "Minecraft");
                } else if (receivedCallback.startsWith("profile-games-csgo")) {
                    updateButton("profile-games-csgo", markup, "CS:GO");
                } else if (receivedCallback.startsWith("profile-games-destiny2")) {
                    updateButton("profile-games-destiny2", markup, "Destiny 2");
                }
            } finally {
                AlterMessage alterMessage = new AlterMessage();
                alterMessage.setMessageId(origin.getMessageId());
                alterMessage.setChatId(origin.getChatId());
                alterMessage.setReplyKeyboard(markup);
                telegramService.sendEditMessage(alterMessage);
            }
        } else {

            /*
            Iterates through the list of buttons, determines if the current one ends with "-chosen"
            as a green flag; if yes -> adds it to the list of games to be updated in database for user
                             if no -> ignores it
             */
            telegramService.deleteMessage(new PurgeMessage(origin.getMessageId(), origin.getChatId()));
            List<String> chosenGames = new ArrayList<>();
            List<List<InlineKeyboardButton>> keyboards = markup.getKeyboard();
            for (List<InlineKeyboardButton> keyboard : keyboards) {
                for (InlineKeyboardButton button : keyboard) {
                    String buttonCallback = button.getCallbackData();
                    if (buttonCallback.endsWith("-chosen")) {
                        String game = buttonCallback.substring(0, buttonCallback.indexOf("-chosen"));
                        chosenGames.add(game);
                    }
                }
            }

            /*
            Iterates through the list of games to be updated in database for user
            creates new Object for every compatible string and sets it for the Credentials object
             */
            if (chosenGames.isEmpty()){
                MultiMessage multiMessage = new MultiMessage();
                multiMessage.setChatId(origin.getChatId());
                multiMessage.setText("(!) You can't help but pick at least one game! First of all, that's what I'm for, why do you want to offend me? Let's try it again ↓");
                ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
                List<KeyboardRow> keyboardRows = new ArrayList<>();
                KeyboardRow row = new KeyboardRow();
                row.add("/games");
                keyboardRows.add(row);
                replyKeyboardMarkup.setKeyboard(keyboardRows);
                multiMessage.setReplyKeyboard(replyKeyboardMarkup);
                telegramService.sendMultiMessage(multiMessage);
            } else {
                for (String gameInList : chosenGames) {
                    switch (gameInList) {
                        case "profile-games-destiny2" -> {
                            Game game = new Game();
                            game.setName(GameENUM.DESTINY);
                            credentials.getGames().add(game);
                        }
                        case "profile-games-csgo" -> {
                            Game game = new Game();
                            game.setName(GameENUM.CSGO);
                            credentials.getGames().add(game);
                        }
                        case "profile-games-minecraft" -> {
                            Game game = new Game();
                            game.setName(GameENUM.MINECRAFT);
                            credentials.getGames().add(game);
                        }
                    }
                }
                credentials.getProgress().setProgressENUM(ProgressENUM.AGE);
                credentialsService.save(credentials);
                MultiMessage multiMessage = new MultiMessage();
                multiMessage.setText("""
                    Okay, I've saved everything.
                    
                    You would be able to change that later but for now, let's move on to the next step.
                    """);
                multiMessage.setChatId(origin.getChatId());
                ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
                List<KeyboardRow> keyboardRows = new ArrayList<>();
                KeyboardRow row = new KeyboardRow();
                row.add("/age");
                keyboardRows.add(row);
                replyKeyboardMarkup.setKeyboard(keyboardRows);
                multiMessage.setReplyKeyboard(replyKeyboardMarkup);
                telegramService.sendMultiMessage(multiMessage);
            }
        }
    }

    private void updateButton(String callbackData, InlineKeyboardMarkup markup, String buttonText) {
        for (List<InlineKeyboardButton> row : markup.getKeyboard()) {
            for (InlineKeyboardButton button : row) {
                if (button.getCallbackData().startsWith(callbackData)) {
                    if (button.getCallbackData().endsWith("-chosen")) {
                        button.setText(buttonText);
                        button.setCallbackData(callbackData);
                    } else {
                        button.setText(buttonText + " ✓");
                        button.setCallbackData(callbackData + "-chosen");
                    }
                }
            }
        }
    }
}
