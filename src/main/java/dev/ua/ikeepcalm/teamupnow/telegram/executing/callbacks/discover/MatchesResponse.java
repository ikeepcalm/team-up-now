package dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.discover;

import dev.ua.ikeepcalm.teamupnow.aop.annotations.I18N;
import dev.ua.ikeepcalm.teamupnow.database.entities.Credentials;
import dev.ua.ikeepcalm.teamupnow.database.entities.Game;
import dev.ua.ikeepcalm.teamupnow.database.entities.Match;
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
public class MatchesResponse extends QueryCallback {
    @Value("${img.matches}")
    String filePath;
    private List<Match> matches;
    private int currentIndex;
    private LocaleTool locale;


    @I18N
    @Override
    @Transactional
    public void manage(String receivedCallback, Message origin, String callbackQueryId) {
        Credentials credentials = credentialsService.findByAccountId(origin.getChatId());
        matches = matchService.findConnectedMatchesForUser(credentials);
        if (matches.size() != 0) {
            int maxIndex = matches.size();
            switch (receivedCallback) {
                case "matches" -> editMessage(origin);
                case "matches-next" -> {
                    if (currentIndex + 1 < maxIndex) {
                        ++currentIndex;
                    }
                    editMessage(origin);
                }
                case "matches-previous" -> {
                    if (currentIndex - 1 >= 0) {
                        --currentIndex;
                    }
                    editMessage(origin);
                }
            }
        } else {
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
                .append("\n");

        stringBuilder
                .append(locale.getMessage("matches-dm"))
                .append("<a href=\"t.me/")
                .append(credentials.getUsername())
                .append("\">")
                .append(locale.getMessage("matches-here"))
                .append("</a>")
                .append("\n");

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
        stringBuilder
                .append("\n")
                .append(locale.getMessage("explore-delimiter"));

        AlterMessage alterMessage = new AlterMessage();
        alterMessage.setMessageId(origin.getMessageId());
        alterMessage.setChatId(origin.getChatId());
        alterMessage.setText(stringBuilder.toString());
        alterMessage.setParseMode("html");
        alterMessage.setFilePath(filePath);
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        List<InlineKeyboardButton> thirdRow = new ArrayList<>();
        InlineKeyboardButton previous = new InlineKeyboardButton();
        previous.setText(locale.getMessage("explore-previous"));
        previous.setCallbackData("matches-previous");
        InlineKeyboardButton next = new InlineKeyboardButton();
        next.setText(locale.getMessage("explore-next"));
        next.setCallbackData("matches-next");
        InlineKeyboardButton back = new InlineKeyboardButton();
        back.setText(locale.getMessage("menu-back"));
        back.setCallbackData("matches-back");
        firstRow.add(previous);
        firstRow.add(next);
        thirdRow.add(back);
        keyboard.add(firstRow);
        keyboard.add(thirdRow);
        inlineKeyboardMarkup.setKeyboard(keyboard);
        alterMessage.setReplyKeyboard(inlineKeyboardMarkup);
        telegramService.sendAlterMessage(alterMessage);
    }


}
