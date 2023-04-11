package dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.menu;

import dev.ua.ikeepcalm.teamupnow.aop.annotations.I18N;
import dev.ua.ikeepcalm.teamupnow.database.dao.service.impls.CredentialsService;
import dev.ua.ikeepcalm.teamupnow.database.entities.Credentials;
import dev.ua.ikeepcalm.teamupnow.database.entities.Game;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.AgeENUM;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.LanguageENUM;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.Executable;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.TelegramService;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.implementations.LocaleTool;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.AlterMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProfileResponse implements Executable {

    @Autowired
    private TelegramService telegramService;
    @Autowired
    private CredentialsService credentialsService;
    private LocaleTool locale;

    @I18N
    @Override
    @Transactional
    public void manage(String receivedCallback, Message origin) {
        Credentials credentials = credentialsService.findByAccountId(origin.getChatId());
        AlterMessage alterMessage = new AlterMessage();
        alterMessage.setMessageId(origin.getMessageId());
        alterMessage.setChatId(origin.getChatId());
        alterMessage.setFileURL("https://static.vecteezy.com/system/resources/previews/005/544/718/original/profile-icon-design-free-vector.jpg");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(locale.getMessage("profile-username-property")).append(credentials.getUsername()).append("\n");
        {
            stringBuilder.append(locale.getMessage("profile-language-property"));
            if (credentials.getUiLanguage() == LanguageENUM.ENGLISH) {
                stringBuilder.append("English \uD83C\uDDEC\uD83C\uDDE7").append("\n");
            } else if (credentials.getUiLanguage() == LanguageENUM.UKRAINIAN) {
                stringBuilder.append("Українська \uD83C\uDDFA\uD83C\uDDE6").append("\n");
            }
        } //UI Language
        {
            stringBuilder.append(locale.getMessage("profile-games-property"));
            int size = credentials.getGames().size();
            int i = 0;
            for (Game game : credentials.getGames()) {
                stringBuilder.append(game.getName());
                if (i != size - 1) {
                    stringBuilder.append(", ");
                }
            }
            stringBuilder.append("\n");
        } //Games
        stringBuilder.append(locale.getMessage("profile-description-property")).append(credentials.getDescription().getDescription()).append("\n");
        {
            stringBuilder.append(locale.getMessage("profile-age-property"));
            if (credentials.getDemographic().getAge() == AgeENUM.YOUNG) {
                stringBuilder.append("14-17").append("\n");
            } else if (credentials.getDemographic().getAge() == AgeENUM.YOUND_ADULT) {
                stringBuilder.append("18-26").append("\n");
            } else if (credentials.getDemographic().getAge() == AgeENUM.ADULT) {
                stringBuilder.append("27-35").append("\n");
            }
        } //Age
        alterMessage.setText(stringBuilder.toString());
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        InlineKeyboardButton editProfile = new InlineKeyboardButton();
        editProfile.setText(locale.getMessage("edit-profile"));
        editProfile.setCallbackData("menu-profile-edit");
        InlineKeyboardButton back = new InlineKeyboardButton();
        back.setText(locale.getMessage("menu-back"));
        back.setCallbackData("menu-back");
        firstRow.add(editProfile);
        firstRow.add(back);
        keyboard.add(firstRow);
        inlineKeyboardMarkup.setKeyboard(keyboard);
        alterMessage.setReplyKeyboard(inlineKeyboardMarkup);
        telegramService.sendAlterMessage(alterMessage);
    }
}
