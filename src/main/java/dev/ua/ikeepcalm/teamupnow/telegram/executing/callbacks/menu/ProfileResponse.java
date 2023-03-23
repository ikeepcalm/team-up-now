package dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.menu;

import dev.ua.ikeepcalm.teamupnow.aop.annotations.Sequenced;
import dev.ua.ikeepcalm.teamupnow.database.dao.service.impls.CredentialsService;
import dev.ua.ikeepcalm.teamupnow.database.entities.Credentials;
import dev.ua.ikeepcalm.teamupnow.database.entities.Game;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.Executable;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.TelegramService;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.MediaMessage;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.PurgeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
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

    @Override
    @Sequenced
    @Transactional
    public void manage(String receivedCallback, Message origin) {
        Credentials credentials = credentialsService.findByAccountId(origin.getChatId());
        MediaMessage message = new MediaMessage();
        message.setFilePath("src/main/resources/profile.png");
        message.setChatId(origin.getChatId());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Username: @").append(credentials.getUsername()).append("\n");
        stringBuilder.append("Language: ").append(credentials.getUiLanguage()).append("\n");
        stringBuilder.append("Age category: ").append(credentials.getDemographic().getAge()).append("\n");
        stringBuilder.append("Description: ").append(credentials.getDescription().getDescription()).append("\n");
        stringBuilder.append("Games: " );
        int size = credentials.getGames().size(); int i = 0;
        for (Game game : credentials.getGames()){
            stringBuilder.append(game.getName());
            if (i != size - 1) {
                stringBuilder.append(", ");
            }
        }
        message.setText(stringBuilder.toString());
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        InlineKeyboardButton editProfile = new InlineKeyboardButton();
        editProfile.setText("Edit Profile \uD83D\uDDD1️");
        editProfile.setCallbackData("menu-profile-edit");
        InlineKeyboardButton back = new InlineKeyboardButton();
        back.setText("Back ↩️");
        back.setCallbackData("menu-back");
        firstRow.add(editProfile);
        firstRow.add(back);
        keyboard.add(firstRow);
        inlineKeyboardMarkup.setKeyboard(keyboard);
        message.setReplyKeyboard(inlineKeyboardMarkup);
        telegramService.sendMediaMessage(message);
    }
}
