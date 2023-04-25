package dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.discover;

import dev.ua.ikeepcalm.teamupnow.aop.annotations.I18N;
import dev.ua.ikeepcalm.teamupnow.database.dao.service.impls.CredentialsService;
import dev.ua.ikeepcalm.teamupnow.database.dao.service.impls.MatchService;
import dev.ua.ikeepcalm.teamupnow.database.entities.Credentials;
import dev.ua.ikeepcalm.teamupnow.database.entities.Game;
import dev.ua.ikeepcalm.teamupnow.database.entities.Match;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.AgeENUM;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.LanguageENUM;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.Executable;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.TelegramService;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.implementations.LocaleTool;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.AlterMessage;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ExploreResponse implements Executable {
    @Value("${img.explore}")
    String filePath;

    @Autowired
    private TelegramService telegramService;
    @Autowired
    private CredentialsService credentialsService;
    @Autowired
    private MatchService matchService;
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
                Match match = matches.get(currentIndex);
                if (credentials == match.getFirstUser()) {
                    match.setFirstUserLiked(true);
                } else if (credentials == match.getSecondUser()) {
                    match.setSecondUserLiked(true);
                }
                if (matches.size() == 1) {
                    matchService.save(match);
                    telegramService.sendAnswerCallbackQuery(locale.getMessage("explore-no-more-results"), callbackQueryId);
                } else {
                    telegramService.sendAnswerCallbackQuery(locale.getMessage("explore-liked"), callbackQueryId);
                    matchService.save(match);
                    matches.remove(match);
                    editMessage(origin);
                }
            }
        } catch (IndexOutOfBoundsException exception){
            telegramService.sendAnswerCallbackQuery(locale.getMessage("explore-no-results"), callbackQueryId);
        }
    }

    private void editMessage(Message origin) {
        Match match = matches.get(currentIndex);
        Credentials credentials;
        if (origin.getChatId().equals(match.getFirstUser().getAccountId())){
            credentials = credentialsService.findByAccountId(match.getSecondUser().getAccountId());
        } else {
            credentials = credentialsService.findByAccountId(match.getFirstUser().getAccountId());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(credentials.getName()).append(" - ").append(match.getScore()).append("%\n");
        {
            if (credentials.getDemographic().getAge() == AgeENUM.YOUNG) {
                stringBuilder.append("14-17 ");
                stringBuilder.append(locale.getMessage("profile-age-property")).append("\n");
            } else if (credentials.getDemographic().getAge() == AgeENUM.YOUND_ADULT) {
                stringBuilder.append("18-26 ");
                stringBuilder.append(locale.getMessage("profile-age-property")).append("\n");
            } else if (credentials.getDemographic().getAge() == AgeENUM.ADULT) {
                stringBuilder.append("27-35 ");
                stringBuilder.append(locale.getMessage("profile-age-property")).append("\n");
            }
        } //Age
        {
            stringBuilder.append(locale.getMessage("speaks"));
            if (credentials.getUiLanguage() == LanguageENUM.ENGLISH) {
                stringBuilder.append("English ")
                        .append(locale.getMessage("profile-language-property"))
                        .append("\n");
            } else if (credentials.getUiLanguage() == LanguageENUM.UKRAINIAN) {
                stringBuilder.append("Українська ")
                        .append(locale.getMessage("profile-language-property"))
                        .append("\n");
                ;
            }
        } //UI Language
        {
            stringBuilder.append(locale.getMessage("profile-games-property"));
            int size = credentials.getGames().size();
            int i = 0;
            for (Game game : credentials.getGames()) {
                stringBuilder.append(game.getName());
                if (i != size) {
                    stringBuilder.append(", ");
                }
            }
            stringBuilder.append("\n");
        } //Games
        stringBuilder.append(locale.getMessage("profile-description-property")).append(credentials.getDescription().getDescription()).append("\n");

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
