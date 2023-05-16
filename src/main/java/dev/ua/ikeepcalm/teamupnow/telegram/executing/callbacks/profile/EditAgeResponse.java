package dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.profile;

import dev.ua.ikeepcalm.teamupnow.aop.annotations.I18N;
import dev.ua.ikeepcalm.teamupnow.database.entities.Credentials;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.AgeENUM;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.SimpleCallback;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.AlterMessage;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.MultiMessage;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.tools.LocaleTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class EditAgeResponse extends SimpleCallback {

    private final EditProfileResponse editProfileResponse;
    private LocaleTool locale;

    public EditAgeResponse(EditProfileResponse editProfileResponse) {
        this.editProfileResponse = editProfileResponse;
    }

    private void manageChoice(String receivedCallback, Message origin){
        Credentials credentials = credentialsService.findByAccountId(origin.getChatId());
        switch (receivedCallback){
            case "edit-profile-age-category-young" -> credentials.getDemographic().setAge(AgeENUM.YOUNG);
            case "edit-profile-age-category-young-adult" -> credentials.getDemographic().setAge(AgeENUM.YOUNG_ADULT);
            case "edit-profile-age-category-adult" -> credentials.getDemographic().setAge(AgeENUM.ADULT);
        } credentialsService.save(credentials);
        matchService.deleteAllMatchesForUser(credentials);
        MultiMessage multiMessage = new MultiMessage();
        multiMessage.setText(locale.getMessage("edit-age-success-response"));
        multiMessage.setChatId(origin.getChatId());
        telegramService.sendMultiMessage(multiMessage);
        editProfileResponse.manage(receivedCallback, origin);
    }

    @I18N
    @Override
    public void manage(String receivedCallback, Message origin) {
        if (receivedCallback.startsWith("edit-profile-age-category")){
            manageChoice(receivedCallback, origin);
        } else {
            AlterMessage alterMessage = new AlterMessage();
            alterMessage.setText(locale.getMessage("profile-age"));
            alterMessage.setMessageId(origin.getMessageId());
            alterMessage.setChatId(origin.getChatId());
            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
            List<InlineKeyboardButton> firstRow = new ArrayList<>();
            InlineKeyboardButton youngCategory = new InlineKeyboardButton();
            youngCategory.setText("14-17");
            youngCategory.setCallbackData("edit-profile-age-category-young");
            InlineKeyboardButton youndAdultCategory = new InlineKeyboardButton();
            youndAdultCategory.setText("18-26");
            youndAdultCategory.setCallbackData("edit-profile-age-category-young-adult");
            InlineKeyboardButton adultCategory = new InlineKeyboardButton();
            adultCategory.setText("27-35");
            adultCategory.setCallbackData("edit-profile-age-category-adult");
            firstRow.add(youngCategory);
            firstRow.add(youndAdultCategory);
            firstRow.add(adultCategory);
            keyboard.add(firstRow);
            inlineKeyboardMarkup.setKeyboard(keyboard);
            alterMessage.setReplyKeyboard(inlineKeyboardMarkup);
            telegramService.sendAlterMessage(alterMessage);
        }
    }
}