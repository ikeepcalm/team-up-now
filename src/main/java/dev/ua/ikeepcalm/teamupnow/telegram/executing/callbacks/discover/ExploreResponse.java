package dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.discover;

import dev.ua.ikeepcalm.teamupnow.aop.annotations.I18N;
import dev.ua.ikeepcalm.teamupnow.database.dao.service.impls.CredentialsService;
import dev.ua.ikeepcalm.teamupnow.database.dao.service.impls.MatchService;
import dev.ua.ikeepcalm.teamupnow.database.entities.Match;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.Executable;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.TelegramService;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.implementations.LocaleTool;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.AlterMessage;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.MultiMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
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
    public void manage(String receivedCallback, Message origin) {
        if (!hasBeenUpdated){
            matchService.createMatchesForUser(credentialsService.findByAccountId(origin.getChatId()));
            hasBeenUpdated = true;
        }
        matches = matchService.findAllMatchesForUser(credentialsService.findByAccountId(origin.getChatId()));
        int maxIndex = matches.size();
        int minIndex = 0;
        if (receivedCallback.equals("explore")) {
            editMessage(origin);
        } else if (receivedCallback.equals("explore-next")) {
            if (currentIndex + 1 < maxIndex){
                ++currentIndex;
            } editMessage(origin);
        } else if (receivedCallback.equals("explore-previous")) {
            if (currentIndex - 1 >= 0){
                --currentIndex;
            }
            editMessage(origin);
        } else if (receivedCallback.equals("explore-like")) {
            //like
            //save
            //remove from matches
        }
    }

    private void editMessage(Message origin) {
        AlterMessage alterMessage = new AlterMessage();
        alterMessage.setMessageId(origin.getMessageId());
        alterMessage.setChatId(origin.getChatId());
        alterMessage.setText(matches.get(currentIndex).toString());
        alterMessage.setFileURL("https://docs.fastlane.tools/img/actions/match.png");
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
