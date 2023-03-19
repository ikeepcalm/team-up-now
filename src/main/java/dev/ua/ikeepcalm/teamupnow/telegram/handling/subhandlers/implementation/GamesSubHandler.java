package dev.ua.ikeepcalm.teamupnow.telegram.handling.subhandlers.implementation;

import dev.ua.ikeepcalm.teamupnow.database.dao.service.impls.CredentialsService;
import dev.ua.ikeepcalm.teamupnow.database.entities.Credentials;
import dev.ua.ikeepcalm.teamupnow.database.entities.Game;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.GameENUM;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.services.TelegramService;
import dev.ua.ikeepcalm.teamupnow.telegram.handling.subhandlers.SubHandler;
import dev.ua.ikeepcalm.teamupnow.telegram.proxies.Callback;
import dev.ua.ikeepcalm.teamupnow.telegram.proxies.EditMessage;
import dev.ua.ikeepcalm.teamupnow.telegram.proxies.ToDelete;
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
public class GamesSubHandler implements SubHandler {

    @Autowired
    private TelegramService telegramService;

    @Autowired
    private CredentialsService credentialsService;

    @Override
    @Transactional
    public void manage(Update update) {
        Credentials credentials = credentialsService.findByAccountId(update.getCallbackQuery().getFrom().getId());
        String currentCallback = update.getCallbackQuery().getData();
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        Message origin = update.getCallbackQuery().getMessage();
        InlineKeyboardMarkup markup = origin.getReplyMarkup();
        if (!currentCallback.equals("profile-games-ready")) {
            try {
                if (currentCallback.startsWith("profile-games-minecraft")) {
                    updateButton("profile-games-minecraft", markup, "Minecraft");
                } else if (currentCallback.startsWith("profile-games-csgo")) {
                    updateButton("profile-games-csgo", markup, "CS:GO");
                } else if (currentCallback.startsWith("profile-games-destiny2")) {
                    updateButton("profile-games-destiny2", markup, "Destiny 2");
                }
            } finally {
                EditMessage editMessage = new EditMessage();
                editMessage.setMessageId(origin.getMessageId());
                editMessage.setChatId(origin.getChatId());
                editMessage.setReplyKeyboard(markup);
                telegramService.sendEditMessage(editMessage);
            }
        } else {

            /*
            Iterates through the list of buttons, determines if the current one ends with "-chosen"
            as a green flag; if yes -> adds it to the list of games to be updated in database for user
                             if no -> ignores it
             */
            telegramService.deleteCallback(new ToDelete(update.getCallbackQuery().getMessage().getMessageId(), chatId));
            List<String> chosenGames = new ArrayList<>();
            List<List<InlineKeyboardButton>> keyboards = markup.getKeyboard();
            for (List<InlineKeyboardButton> keyboard : keyboards) {
                for (InlineKeyboardButton button : keyboard) {
                    String callbackData = button.getCallbackData();
                    if (callbackData.endsWith("-chosen")) {
                        String game = callbackData.substring(0, callbackData.indexOf("-chosen"));
                        chosenGames.add(game);
                    }
                }
            }

            /*
            Iterates through the list of games to be updated in database for user
            creates new Object for every compatible string and sets it for the Credentials object
             */
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
            credentialsService.save(credentials);
            Callback callback = new Callback();
            callback.setText("""
                    Okay, I've saved everything.
                    
                    You would be able to change that later but for now, let's move on to the next step.
                    """);
            callback.setChatId(update.getCallbackQuery().getMessage().getChatId());
            ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
            List<KeyboardRow> keyboardRows = new ArrayList<>();
            KeyboardRow row = new KeyboardRow();
            row.add("/age");
            keyboardRows.add(row);
            replyKeyboardMarkup.setKeyboard(keyboardRows);
            callback.setReplyKeyboard(replyKeyboardMarkup);
            telegramService.sendCallback(callback);
        }
    }

    /*
    Iterates through the list of buttons, determines if the current one ends with "-chosen"
    as a green flag; if yes -> deletes it
                     if no -> adds it
    */
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
