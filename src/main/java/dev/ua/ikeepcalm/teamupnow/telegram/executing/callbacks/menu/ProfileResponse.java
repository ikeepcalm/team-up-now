package dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.menu;

import dev.ua.ikeepcalm.teamupnow.database.entities.Credentials;
import dev.ua.ikeepcalm.teamupnow.database.entities.Game;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.AgeENUM;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.LanguageENUM;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.SimpleCallback;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.AlterMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class ProfileResponse extends SimpleCallback {

    @Value("${img.profile}")
    String filePath;

    @Override
    @Transactional
    public void manage(String receivedCallback, CallbackQuery origin) {
        ResourceBundle locale = getBundle(origin);
        Credentials credentials = credentialsService.findByAccountId(origin.getMessage().getChatId());
        AlterMessage alterMessage = new AlterMessage();
        alterMessage.setMessageId(origin.getMessage().getMessageId());
        alterMessage.setChatId(origin.getMessage().getChatId());
        alterMessage.setFilePath(filePath);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(locale.getString("profile-delimiter")).append("\n")
                .append("\n")
                .append(locale.getString("profile-tokens-property"))
                .append(credentials.getConnectionTokens())
                .append("/")
                .append(credentials.getSustainableTokens())
                .append("\n")
                .append(locale.getString("profile-username-property"))
                .append(credentials.getUsername())
                .append("\n");
        {
        stringBuilder.append(locale.getString("profile-language-property"));
        if (credentials.getUiLanguage() == LanguageENUM.ENGLISH) {
            stringBuilder.append("English")
                    .append("\n");
        } else if (credentials.getUiLanguage() == LanguageENUM.UKRAINIAN) {
            stringBuilder.append("Українська")
                    .append("\n");
        }
        }//Language
        {
            stringBuilder.append(locale.getString("profile-age-property"));
            if (credentials.getDemographic().getAge() == AgeENUM.YOUNG) {
                stringBuilder.append("14-17 ");
                stringBuilder.append(locale.getString("years-old")).append("\n");
            } else if (credentials.getDemographic().getAge() == AgeENUM.YOUNG_ADULT) {
                stringBuilder.append("18-26 ");
                stringBuilder.append(locale.getString("years-old")).append("\n");
            } else if (credentials.getDemographic().getAge() == AgeENUM.ADULT) {
                stringBuilder.append("27-35 ");
                stringBuilder.append(locale.getString("years-old")).append("\n");
            }
        } //Age
        {
            stringBuilder.append(locale.getString("profile-games-property"));
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
                .append(locale.getString("profile-description-property"))
                .append(credentials.getDescription().getDescription())
                .append("\n")
                .append("\n")
                .append(locale.getString("profile-delimiter"))
                .append("\n");


        alterMessage.setText(stringBuilder.toString());
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        InlineKeyboardButton editProfile = new InlineKeyboardButton();
        editProfile.setText(locale.getString("edit-profile"));
        editProfile.setCallbackData("menu-profile-edit");
        InlineKeyboardButton back = new InlineKeyboardButton();
        back.setText(locale.getString("menu-back"));
        back.setCallbackData("menu-back");
        firstRow.add(editProfile);
        firstRow.add(back);
        keyboard.add(firstRow);
        inlineKeyboardMarkup.setKeyboard(keyboard);
        alterMessage.setReplyKeyboard(inlineKeyboardMarkup);
        telegramService. sendAlterMessage(alterMessage);
    }
}
