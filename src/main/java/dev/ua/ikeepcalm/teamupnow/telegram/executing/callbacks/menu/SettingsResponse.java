package dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.menu;

import dev.ua.ikeepcalm.teamupnow.aop.annotations.I18N;
import dev.ua.ikeepcalm.teamupnow.database.dao.service.impls.CredentialsService;
import dev.ua.ikeepcalm.teamupnow.database.entities.Credentials;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.LanguageENUM;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.Executable;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.TelegramService;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.implementations.LocaleTool;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.AlterMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class SettingsResponse implements Executable {

    @Autowired
    private TelegramService telegramService;
    @Autowired
    private CredentialsService credentialsService;
    private LocaleTool locale;

    @I18N
    @Override
    public void manage(String receivedCallback, Message origin) {
        Credentials credentials = credentialsService.findByAccountId(origin.getChatId());
        AlterMessage alterMessage = new AlterMessage();
        alterMessage.setFileURL("https://upload.wikimedia.org/wikipedia/commons/thumb/5/58/Ic_settings_48px.svg/2048px-Ic_settings_48px.svg.png");
        alterMessage.setMessageId(origin.getMessageId());
        alterMessage.setChatId(origin.getChatId());
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        List<InlineKeyboardButton> secondRow = new ArrayList<>();
        InlineKeyboardButton language = new InlineKeyboardButton();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(locale.getMessage("profile-language-property"));
        if (credentials.getUiLanguage().equals(LanguageENUM.UKRAINIAN)){
            stringBuilder.append("\uD83C\uDDFA\uD83C\uDDE6");
        } else {
            stringBuilder.append("\uD83C\uDDEC\uD83C\uDDE7");
        }
        language.setText(stringBuilder.toString());
        language.setCallbackData("menu-settings-lang");
        InlineKeyboardButton deletion = new InlineKeyboardButton();
        deletion.setText(locale.getMessage("delete-account"));
        deletion.setCallbackData("menu-settings-delete-account");
        InlineKeyboardButton back = new InlineKeyboardButton();
        back.setText(locale.getMessage("menu-back"));
        back.setCallbackData("menu-back");
        firstRow.add(language);
        firstRow.add(deletion);
        secondRow.add(back);
        keyboard.add(firstRow);
        keyboard.add(secondRow);
        inlineKeyboardMarkup.setKeyboard(keyboard);
        alterMessage.setReplyKeyboard(inlineKeyboardMarkup);
        telegramService.sendAlterMessage(alterMessage);
    }
}
