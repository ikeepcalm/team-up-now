package dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.discover;

import dev.ua.ikeepcalm.teamupnow.database.entities.Credentials;
import dev.ua.ikeepcalm.teamupnow.database.entities.Game;
import dev.ua.ikeepcalm.teamupnow.database.entities.Match;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.AgeENUM;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.LanguageENUM;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.QueryCallback;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.AlterMessage;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.MultiMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Component
@Scope(value = "prototype")
public class ExploreResponse extends QueryCallback {
    @Value("${img.explore}")
    String filePath;
    private List<Match> matches;
    private int currentIndex;
    private boolean hasBeenUpdated = false;
    private ResourceBundle locale;

    @Override
    @Transactional
    public void manage(String receivedCallback, CallbackQuery origin, String callbackQueryId) {
        locale = getBundle(origin);
        Credentials credentials = credentialsService.findByAccountId(origin.getMessage().getChatId());
        if (!credentials.getUsername().equals(origin.getMessage().getFrom().getUserName())){
            credentials.setUsername(origin.getFrom().getUserName());
            credentialsService.save(credentials);
        } try {
            if (!hasBeenUpdated) {
                matchService.createMatchesForUser(credentials);
                hasBeenUpdated = true;
            }
            matches = matchService.findAllMatchesForUser(credentials);
            int maxIndex = matches.size();
            switch (receivedCallback) {
                case "explore" -> editMessage(origin.getMessage());
                case "explore-next" -> {
                    if (currentIndex + 1 < maxIndex) {
                        ++currentIndex;
                    }
                    editMessage(origin.getMessage());
                }
                case "explore-previous" -> {
                    if (currentIndex - 1 >= 0) {
                        --currentIndex;
                    }
                    editMessage(origin.getMessage());
                }
                case "explore-like" -> {
                    if (credentials.getConnectionTokens() > 0) {
                        Match match = matches.get(currentIndex);
                        if (credentials == match.getFirstUser()) {
                            match.setFirstUserLiked(true);
                            if (match.isSecondUserLiked()) {
                                MultiMessage message = new MultiMessage();
                                message.setText(locale.getString("explore-notification"));
                                message.setChatId(match.getSecondUser().getAccountId());
                                telegramService.sendMultiMessage(message);
                            }
                        } else if (credentials == match.getSecondUser()) {
                            match.setSecondUserLiked(true);
                            if (match.isFirstUserLiked()) {
                                MultiMessage message = new MultiMessage();
                                message.setText(locale.getString("explore-notification"));
                                message.setChatId(match.getFirstUser().getAccountId());
                                telegramService.sendMultiMessage(message);
                            }
                        }
                        if (matches.size() == 1) {
                            credentials.setConnectionTokens(credentials.getConnectionTokens() - 1);
                            matchService.save(match);
                            telegramService.sendAnswerCallbackQuery(locale.getString("explore-no-more-results"), callbackQueryId);
                        } else {
                            credentials.setConnectionTokens(credentials.getConnectionTokens() - 1);
                            String message = locale.getString("explore-liked") + " " + credentials.getConnectionTokens() + "/" + credentials.getSustainableTokens();
                            telegramService.sendAnswerCallbackQuery(message, callbackQueryId);
                            matchService.save(match);
                            matches.remove(match);
                            editMessage(origin.getMessage());
                        }
                        credentialsService.save(credentials);
                    } else {
                        telegramService.sendAnswerCallbackQuery(locale.getString("explore-no-tokens"), callbackQueryId);
                    }
                } case "explore-hide" ->{
                    Match match = matches.get(currentIndex);
                    match.setHidden(true);
                    matchService.save(match);
                    matches.remove(match);
                    if (matches.size() == 1) {
                        credentials.setConnectionTokens(credentials.getConnectionTokens() - 1);
                        matchService.save(match);
                        telegramService.sendAnswerCallbackQuery(locale.getString("explore-no-more-results"), callbackQueryId);
                    } else {
                        editMessage(origin.getMessage());
                    }
                }
            }
        } catch (IndexOutOfBoundsException exception) {
            telegramService.sendAnswerCallbackQuery(locale.getString("explore-no-results"), callbackQueryId);
        }
    }

    private void editMessage(Message origin) {
        Match match = matches.get(currentIndex);
        Credentials credentials;
        if (origin.getChatId().equals(match.getFirstUser().getAccountId())) {
            credentials = credentialsService.findByAccountId(match.getSecondUser().getAccountId());
        } else {
            credentials = credentialsService.findByAccountId(match.getFirstUser().getAccountId());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(locale.getString("explore-delimiter"))
                .append("\n")
                .append(locale.getString("explore-score"))
                .append("\n")
                .append(locale.getString("explore-arrow"))
                .append("\n")
                .append(locale.getString("explore-score-percentage"))
                .append(match.getScore()).append("%")
                .append("\n")
                .append(locale.getString("explore-delimiter"))
                .append("\n").append("\n");

        stringBuilder
                .append(locale.getString("explore-name"))
                .append(credentials.getName())
                .append("\n");
        {
            stringBuilder.append(locale.getString("explore-language"));
            if (credentials.getUiLanguage() == LanguageENUM.ENGLISH) {
                stringBuilder.append("English ")
                        .append("\n");
            } else if (credentials.getUiLanguage() == LanguageENUM.UKRAINIAN) {
                stringBuilder.append("Українська ")
                        .append("\n");
            }
        } //Language
        {
            stringBuilder.append(locale.getString("explore-age"));
            if (credentials.getDemographic().getAge() == AgeENUM.YOUNG) {
                stringBuilder.append("14-17 ");
            } else if (credentials.getDemographic().getAge() == AgeENUM.YOUNG_ADULT) {
                stringBuilder.append("18-26 ");
            } else if (credentials.getDemographic().getAge() == AgeENUM.ADULT) {
                stringBuilder.append("27-35 ");
            }
            stringBuilder.append(locale.getString("years-old")).append("\n");
        } //Age
        {
            stringBuilder.append(locale.getString("explore-games"));
            int size = credentials.getGames().size();
            int i = 0;
            for (Game game : credentials.getGames()) {
                stringBuilder.append(game.getName().getButtonText());
                if (i < size - 1) {
                    stringBuilder.append(", ");
                    ++i;
                }
            }
            stringBuilder.append("\n");
        } //Games
        stringBuilder
                .append(locale.getString("explore-description"))
                .append(credentials.getDescription().getDescription())
                .append("\n")
                .append("\n")
                .append(locale.getString("explore-delimiter"));

        AlterMessage alterMessage = new AlterMessage();
        alterMessage.setMessageId(origin.getMessageId());
        alterMessage.setChatId(origin.getChatId());
        alterMessage.setText(stringBuilder.toString());
        alterMessage.setFilePath(filePath);
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        List<InlineKeyboardButton> secondRow = new ArrayList<>();
        List<InlineKeyboardButton> thirdRow = new ArrayList<>();
        List<InlineKeyboardButton> fourthRow = new ArrayList<>();
        InlineKeyboardButton previous = new InlineKeyboardButton();
        previous.setText(locale.getString("explore-previous"));
        previous.setCallbackData("explore-previous");
        InlineKeyboardButton next = new InlineKeyboardButton();
        next.setText(locale.getString("explore-next"));
        next.setCallbackData("explore-next");
        InlineKeyboardButton like = new InlineKeyboardButton();
        like.setText(locale.getString("explore-like"));
        like.setCallbackData("explore-like");
        InlineKeyboardButton hide = new InlineKeyboardButton();
        hide.setText(locale.getString("explore-hide"));
        hide.setCallbackData("explore-hide");
        InlineKeyboardButton back = new InlineKeyboardButton();
        back.setText(locale.getString("menu-back"));
        back.setCallbackData("explore-back");
        firstRow.add(previous);
        firstRow.add(next);
        secondRow.add(like);
        thirdRow.add(hide);
        fourthRow.add(back);
        keyboard.add(firstRow);
        keyboard.add(secondRow);
        keyboard.add(thirdRow);
        keyboard.add(fourthRow);
        inlineKeyboardMarkup.setKeyboard(keyboard);
        alterMessage.setReplyKeyboard(inlineKeyboardMarkup);
        telegramService.sendAlterMessage(alterMessage);
    }
}
