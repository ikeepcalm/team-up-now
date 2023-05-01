package dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.discover;

import dev.ua.ikeepcalm.teamupnow.aop.annotations.I18N;
import dev.ua.ikeepcalm.teamupnow.database.entities.Credentials;
import dev.ua.ikeepcalm.teamupnow.database.entities.Game;
import dev.ua.ikeepcalm.teamupnow.database.entities.Match;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.AgeENUM;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.LanguageENUM;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.QueryCallback;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.AlterMessage;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.tools.LocaleTool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope(value = "prototype")
public class ExploreResponse extends QueryCallback {
    @Value("${img.explore}")
    String filePath;
    private List<Match> matches;
    private int currentIndex;
    private boolean hasBeenUpdated = false;
    private LocaleTool locale;

    @I18N
    @Override
    @Transactional
    public void manage(String receivedCallback, Message origin, String callbackQueryId) {
        try {
            if (!hasBeenUpdated) {
                matchService.createMatchesForUser(credentialsService.findByAccountId(origin.getChatId()));
                hasBeenUpdated = true;
            }
            matches = matchService.findAllMatchesForUser(credentialsService.findByAccountId(origin.getChatId()));
            int maxIndex = matches.size();
            int minIndex = 0;
            if (receivedCallback.equals("explore")) {
                editMessage(origin);
            } else if (receivedCallback.equals("explore-next")) {
                if (currentIndex + 1 < maxIndex) {
                    ++currentIndex;
                }
                editMessage(origin);
            } else if (receivedCallback.equals("explore-previous")) {
                if (currentIndex - 1 >= 0) {
                    --currentIndex;
                }
                editMessage(origin);
            } else if (receivedCallback.equals("explore-like")) {
                Credentials credentials = credentialsService.findByAccountId(origin.getChatId());
                if (credentials.getConnectionTokens() > 0) {
                    Match match = matches.get(currentIndex);
                    if (credentials == match.getFirstUser()) {
                        match.setFirstUserLiked(true);
                    } else if (credentials == match.getSecondUser()) {
                        match.setSecondUserLiked(true);
                    }
                    if (matches.size() == 1) {
                        credentials.setConnectionTokens(credentials.getConnectionTokens() - 1);
                        matchService.save(match);
                        telegramService.sendAnswerCallbackQuery(locale.getMessage("explore-no-more-results"), callbackQueryId);
                    } else {
                        credentials.setConnectionTokens(credentials.getConnectionTokens() - 1);
                        String message = locale.getMessage("explore-liked") + " " + credentials.getConnectionTokens() + "/" + credentials.getSustainableTokens();
                        telegramService.sendAnswerCallbackQuery(message, callbackQueryId);
                        matchService.save(match);
                        matches.remove(match);
                        editMessage(origin);
                    }credentialsService.save(credentials);
                } else {
                    telegramService.sendAnswerCallbackQuery(locale.getMessage("explore-no-tokens"), callbackQueryId);
                }
            }
        } catch (IndexOutOfBoundsException exception) {
            telegramService.sendAnswerCallbackQuery(locale.getMessage("explore-no-results"), callbackQueryId);
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
                .append(locale.getMessage("explore-delimiter"))
                .append("\n")
                .append(locale.getMessage("explore-score"))
                .append("\n")
                .append(locale.getMessage("explore-arrow"))
                .append("\n")
                .append(locale.getMessage("explore-score-percentage"))
                .append(match.getScore()).append("%")
                .append("\n")
                .append(locale.getMessage("explore-delimiter"))
                .append("\n").append("\n");

        stringBuilder
                .append(locale.getMessage("explore-name"))
                .append(credentials.getName())
                .append("\n");
        {
            stringBuilder.append(locale.getMessage("explore-language"));
            if (credentials.getUiLanguage() == LanguageENUM.ENGLISH) {
                stringBuilder.append("English ")
                        .append("\n");
            } else if (credentials.getUiLanguage() == LanguageENUM.UKRAINIAN) {
                stringBuilder.append("Українська ")
                        .append("\n");
            }
        } //Language
        {
            stringBuilder.append(locale.getMessage("explore-games"));
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
        {
            stringBuilder.append(locale.getMessage("explore-age"));
            if (credentials.getDemographic().getAge() == AgeENUM.YOUNG) {
                stringBuilder.append("14-17 ");
            } else if (credentials.getDemographic().getAge() == AgeENUM.YOUND_ADULT) {
                stringBuilder.append("18-26 ");
            } else if (credentials.getDemographic().getAge() == AgeENUM.ADULT) {
                stringBuilder.append("27-35 ");
            }
            stringBuilder.append(locale.getMessage("years-old")).append("\n");
        } //Age

        stringBuilder
                .append(locale.getMessage("explore-description"))
                .append(credentials.getDescription().getDescription())
                .append("\n")
                .append("\n")
                .append(locale.getMessage("explore-delimiter"));

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
        InlineKeyboardButton previous = new InlineKeyboardButton();
        previous.setText(locale.getMessage("explore-previous"));
        previous.setCallbackData("explore-previous");
        InlineKeyboardButton next = new InlineKeyboardButton();
        next.setText(locale.getMessage("explore-next"));
        next.setCallbackData("explore-next");
        InlineKeyboardButton like = new InlineKeyboardButton();
        like.setText(locale.getMessage("explore-like"));
        like.setCallbackData("explore-like");
        InlineKeyboardButton back = new InlineKeyboardButton();
        back.setText(locale.getMessage("menu-back"));
        back.setCallbackData("explore-back");
        firstRow.add(previous);
        firstRow.add(next);
        secondRow.add(like);
        thirdRow.add(back);
        keyboard.add(firstRow);
        keyboard.add(secondRow);
        keyboard.add(thirdRow);
        inlineKeyboardMarkup.setKeyboard(keyboard);
        alterMessage.setReplyKeyboard(inlineKeyboardMarkup);
        telegramService.sendAlterMessage(alterMessage);
    }
}
