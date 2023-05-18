package dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.profile;

import dev.ua.ikeepcalm.teamupnow.database.entities.Credentials;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.AgeENUM;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.SimpleCallback;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.AlterMessage;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.MultiMessage;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class EditAgeResponse extends SimpleCallback {

    private final EditProfileResponse editProfileResponse;
    private ResourceBundle locale;

    public EditAgeResponse(EditProfileResponse editProfileResponse) {
        this.editProfileResponse = editProfileResponse;
    }

    private void manageChoice(String receivedCallback, CallbackQuery origin){
        Credentials credentials = credentialsService.findByAccountId(origin.getMessage().getChatId());
        switch (receivedCallback){
            case "edit-profile-age-category-young" -> credentials.getDemographic().setAge(AgeENUM.YOUNG);
            case "edit-profile-age-category-young-adult" -> credentials.getDemographic().setAge(AgeENUM.YOUNG_ADULT);
            case "edit-profile-age-category-adult" -> credentials.getDemographic().setAge(AgeENUM.ADULT);
        } credentialsService.save(credentials);
        matchService.deleteAllMatchesForUser(credentials);
        MultiMessage multiMessage = new MultiMessage();
        multiMessage.setText(locale.getString("edit-age-success-response"));
        multiMessage.setChatId(origin.getMessage().getChatId());
        telegramService.sendMultiMessage(multiMessage);
        editProfileResponse.manage(receivedCallback, origin);
    }

    @Override
    public void manage(String receivedCallback, CallbackQuery origin) {
        locale = getBundle(origin);
        if (receivedCallback.startsWith("edit-profile-age-category")){
            manageChoice(receivedCallback, origin);
        } else {
            AlterMessage alterMessage = new AlterMessage();
            alterMessage.setText(locale.getString("profile-age"));
            alterMessage.setMessageId(origin.getMessage().getMessageId());
            alterMessage.setChatId(origin.getMessage().getChatId());
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