package dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.profile;

import dev.ua.ikeepcalm.teamupnow.database.dao.service.impls.MatchService;
import dev.ua.ikeepcalm.teamupnow.database.entities.Credentials;
import dev.ua.ikeepcalm.teamupnow.database.entities.Game;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.GameENUM;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.SimpleCallback;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.mediators.ResponseMediator;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.AlterMessage;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.MultiMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Component
@Scope(value = "prototype")
public class EditGamesResponse extends SimpleCallback {

    @Value("${img.profile}")
    String filePath;

    private ResourceBundle locale;
    private final List<String> selectedGamesCallbacks = new ArrayList<>();
    private int page = 1;
    private boolean rendered = false;

    private final ResponseMediator responseMediator;
    private final MatchService matchService;

    @Autowired
    public EditGamesResponse(ResponseMediator responseMediator, MatchService matchService) {
        this.responseMediator = responseMediator;
        this.matchService = matchService;
    }


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
        if (receivedCallback.equals("edit-profile-games-next")) {
            ++page;
            editMessage(origin);
        } else if (receivedCallback.equals("edit-profile-games-back")) {
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
            String callbackData = GameENUM.values()[i].getEditCallback();
            button.setText(selectedGamesCallbacks.contains(callbackData) ? GameENUM.values()[i].getButtonText() + " ✓" : GameENUM.values()[i].getButtonText());
            button.setCallbackData(callbackData);
            row.add(button);
            if (i + 1 < end) {
                InlineKeyboardButton button1 = new InlineKeyboardButton();
                callbackData = "edit-profile-games-" + GameENUM.values()[i + 1];
                button1.setText(selectedGamesCallbacks.contains(callbackData) ? GameENUM.values()[i + 1].getButtonText() + " ✓" : GameENUM.values()[i + 1].getButtonText());
                button1.setCallbackData(callbackData);
                row.add(button1);
            }
            rows.add(row);
        }

        List<InlineKeyboardButton> paginationRow = new ArrayList<>();
        if (page > 1) {
            InlineKeyboardButton backButton = new InlineKeyboardButton();
            backButton.setText(locale.getString("explore-previous"));
            backButton.setCallbackData("edit-profile-games-back");
            paginationRow.add(backButton);
        }
        if (end < GameENUM.values().length) {
            InlineKeyboardButton backButton = new InlineKeyboardButton();
            backButton.setText(locale.getString("explore-next"));
            backButton.setCallbackData("edit-profile-games-next");
            paginationRow.add(backButton);
        }
        rows.add(paginationRow);

        List<InlineKeyboardButton> readyRow = new ArrayList<>();
        InlineKeyboardButton ready = new InlineKeyboardButton();
        ready.setText(locale.getString("profile-ready"));
        ready.setCallbackData("edit-profile-games-ready");
        readyRow.add(ready);
        rows.add(readyRow);

        inlineKeyboardMarkup.setKeyboard(rows);
        alterMessage.setReplyKeyboard(inlineKeyboardMarkup);
        telegramService.sendAlterMessage(alterMessage);
    }


    @Override
    @Transactional
    public void manage(String receivedCallback, CallbackQuery origin) {
        locale = getBundle(origin);
        if (rendered) {
            if (receivedCallback.equals("edit-profile-games-ready")) {
                if (selectedGamesCallbacks.isEmpty()) {
                    MultiMessage multiMessage = new MultiMessage();
                    multiMessage.setChatId(origin.getMessage().getChatId());
                    multiMessage.setText(locale.getString("games-error-response"));
                    telegramService.sendMultiMessage(multiMessage);
                    responseMediator.executeEditProfileResponse(receivedCallback, origin);
                } else {
                    Credentials credentialsToSave = credentialsService.findByAccountId(origin.getMessage().getChatId());
                    credentialsToSave.getGames().clear();
                    credentialsService.save(credentialsToSave);
                    for (String callback : selectedGamesCallbacks) {
                        for (GameENUM gameENUM : GameENUM.values()) {
                            if (callback.equals(gameENUM.getEditCallback())) {
                                Game game = new Game();
                                game.setName(gameENUM);
                                game.setCredentials(credentialsToSave);
                                credentialsToSave.getGames().add(game);
                            }
                        }
                    }
                    credentialsService.save(credentialsToSave);
                    matchService.deleteAllMatchesForUser(credentialsToSave);
                    MultiMessage multiMessage = new MultiMessage();
                    multiMessage.setText(locale.getString("edit-games-success-response"));
                    multiMessage.setChatId(origin.getMessage().getChatId());
                    String chosenGames = credentialsToSave.getGames().stream()
                            .map(game -> game.getName().name())
                            .collect(Collectors.joining(", "));
                    logger.info("(\uD83C\uDFAE) User [@" + origin.getFrom().getUserName() + "] edited his games: " + chosenGames);
                    telegramService.sendMultiMessage(multiMessage);
                    responseMediator.executeEditProfileResponse(receivedCallback, origin);
                    }
            } else if (receivedCallback.equals("edit-profile-games-next") || receivedCallback.equals("edit-profile-games-back")) {
                managePaginationButton(receivedCallback, origin.getMessage());
            } else if (receivedCallback.startsWith("edit-profile-games")) {
                manageGamesButton(receivedCallback, origin.getMessage());
            }
        } else {
            rendered = true;
            Credentials credentials = credentialsService.findByAccountId(origin.getMessage().getChatId());
            for (Game game : credentials.getGames()) {
                selectedGamesCallbacks.add(game.getName().getEditCallback());
            }
            AlterMessage alterMessage = new AlterMessage();
            alterMessage.setText(locale.getString("profile-games"));
            alterMessage.setMessageId(origin.getMessage().getMessageId());
            alterMessage.setChatId(origin.getMessage().getChatId());
            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            GameENUM[] gameValues = GameENUM.values();
            List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
            List<InlineKeyboardButton> row = new ArrayList<>();
            for (int i = 0; i < gameValues.length; i++) {
                String callbackData = GameENUM.values()[i].getEditCallback();
                InlineKeyboardButton button = new InlineKeyboardButton();
                button.setText(selectedGamesCallbacks.contains(callbackData) ? GameENUM.values()[i].getButtonText() + " ✓" : GameENUM.values()[i].getButtonText());
                if (selectedGamesCallbacks.contains(gameValues[i].getEditCallback())) {
                    button.setCallbackData(gameValues[i].getEditCallback());
                }
                button.setCallbackData(gameValues[i].getEditCallback());
                row.add(button);
                if (row.size() == 2 || i == gameValues.length - 1) {
                    if (keyboard.size() == 4 &&
                            keyboard.get(0).size() == 2 &&
                            keyboard.get(1).size() == 2 &&
                            keyboard.get(2).size() == 2) {
                    } else {
                        keyboard.add(row);
                        row = new ArrayList<>();
                        if (keyboard.size() == 3 && i != gameValues.length - 1) {
                            InlineKeyboardButton nextButton = new InlineKeyboardButton();
                            nextButton.setText(locale.getString("explore-next"));
                            nextButton.setCallbackData("edit-profile-games-next");
                            List<InlineKeyboardButton> paginationRow = new ArrayList<>();
                            paginationRow.add(nextButton);
                            keyboard.add(paginationRow);
                        }
                    }
                }
            }

            List<InlineKeyboardButton> readyRow = new ArrayList<>();
            InlineKeyboardButton ready = new InlineKeyboardButton();
            ready.setText(locale.getString("profile-ready"));
            ready.setCallbackData("edit-profile-games-ready");
            readyRow.add(ready);
            keyboard.add(readyRow);
            inlineKeyboardMarkup.setKeyboard(keyboard);
            alterMessage.setReplyKeyboard(inlineKeyboardMarkup);
            telegramService.sendAlterMessage(alterMessage);
        }
    }
}
