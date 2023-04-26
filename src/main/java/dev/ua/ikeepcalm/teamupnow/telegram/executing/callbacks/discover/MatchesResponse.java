package dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.discover;

import dev.ua.ikeepcalm.teamupnow.aop.annotations.I18N;
import dev.ua.ikeepcalm.teamupnow.database.entities.Credentials;
import dev.ua.ikeepcalm.teamupnow.database.entities.Match;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.QueryCallback;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.SimpleCallback;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.AlterMessage;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.tools.LocaleTool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class MatchesResponse extends QueryCallback {
    @Value("${img.matches}")
    String filePath;
    private LocaleTool locale;

    @I18N
    @Override
    @Transactional
    public void manage(String receivedCallback, Message origin, String callbackQueryId) {
        Credentials credentials = credentialsService.findByAccountId(origin.getChatId());
        List<Match> matches = matchService.findConnectedMatchesForUser(credentials);
        if (matches.size() != 0){
            StringBuilder stringBuilder = new StringBuilder();
            AlterMessage alterMessage = new AlterMessage();
            alterMessage.setParseMode("html");
            for (Match match : matches){
                if (credentials == match.getFirstUser()){
                    stringBuilder
                            .append("<a href = \"t.me/")
                            .append(match.getSecondUser().getUsername())
                            .append("\">")
                            .append(match.getSecondUser().getName())
                            .append("</a>")
                            .append("\n");
                } else if (credentials == match.getSecondUser()){
                    stringBuilder
                            .append("<a href = \"t.me/")
                            .append(match.getFirstUser().getUsername())
                            .append("\">")
                            .append(match.getFirstUser().getName())
                            .append("</a>")
                            .append("\n");
                }
            } alterMessage.setText(stringBuilder.toString());
            alterMessage.setChatId(origin.getChatId());
            alterMessage.setFilePath(filePath);
            alterMessage.setMessageId(origin.getMessageId());
            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
            List<InlineKeyboardButton> firstRow = new ArrayList<>();
            InlineKeyboardButton back = new InlineKeyboardButton();
            back.setText(locale.getMessage("menu-back"));
            back.setCallbackData("explore-back");
            firstRow.add(back);
            keyboard.add(firstRow);
            inlineKeyboardMarkup.setKeyboard(keyboard);
            alterMessage.setReplyKeyboard(inlineKeyboardMarkup);
            telegramService.sendAlterMessage(alterMessage);
        } else {
            telegramService.sendAnswerCallbackQuery(locale.getMessage("explore-no-results"), callbackQueryId);
        }
    }
}
