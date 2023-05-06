package dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.profile;

import dev.ua.ikeepcalm.teamupnow.aop.annotations.I18N;
import dev.ua.ikeepcalm.teamupnow.database.entities.Credentials;
import dev.ua.ikeepcalm.teamupnow.database.entities.Game;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.AgeENUM;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.LanguageENUM;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.SimpleCallback;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.MultiMessage;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.PurgeMessage;
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
public class EditBackResponse extends SimpleCallback {

    @Value("${img.profile}")
    String filePath;
    private LocaleTool locale;

    @I18N
    @Override
    @Transactional
    public void manage(String receivedCallback, Message origin) {
        telegramService.sendPurgeMessage(new PurgeMessage(origin.getMessageId(), origin.getChatId()));
        Credentials credentials = credentialsService.findByAccountId(origin.getChatId());
        MultiMessage multiMessage = new MultiMessage();
        multiMessage.setChatId(origin.getChatId());
        multiMessage.setFilePath(filePath);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(locale.getMessage("profile-delimiter")).append("\n")
                .append("\n")
                .append(locale.getMessage("profile-tokens-property"))
                .append(credentials.getConnectionTokens())
                .append("/")
                .append(credentials.getSustainableTokens())
                .append("\n")
                .append(locale.getMessage("profile-username-property"))
                .append(credentials.getUsername())
                .append("\n");
        {
            stringBuilder.append(locale.getMessage("profile-language-property"));
            if (credentials.getUiLanguage() == LanguageENUM.ENGLISH) {
                stringBuilder.append("English")
                        .append("\n");
            } else if (credentials.getUiLanguage() == LanguageENUM.UKRAINIAN) {
                stringBuilder.append("Українська")
                        .append("\n");
            }
        }//Language
        {
            stringBuilder.append(locale.getMessage("profile-age-property"));
            if (credentials.getDemographic().getAge() == AgeENUM.YOUNG) {
                stringBuilder.append("14-17 ");
                stringBuilder.append(locale.getMessage("years-old")).append("\n");
            } else if (credentials.getDemographic().getAge() == AgeENUM.YOUND_ADULT) {
                stringBuilder.append("18-26 ");
                stringBuilder.append(locale.getMessage("years-old")).append("\n");
            } else if (credentials.getDemographic().getAge() == AgeENUM.ADULT) {
                stringBuilder.append("27-35 ");
                stringBuilder.append(locale.getMessage("years-old")).append("\n");
            }
        } //Age
        {
            stringBuilder.append(locale.getMessage("profile-games-property"));
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
                .append(locale.getMessage("profile-description-property"))
                .append(credentials.getDescription().getDescription())
                .append("\n")
                .append("\n")
                .append(locale.getMessage("profile-delimiter"))
                .append("\n");


        multiMessage.setText(stringBuilder.toString());
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
        multiMessage.setReplyKeyboard(inlineKeyboardMarkup);
        telegramService.sendMultiMessage(multiMessage);
    }
}
