package dev.ua.ikeepcalm.teamupnow.telegram.handling.handlers;

import dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.*;
import dev.ua.ikeepcalm.teamupnow.telegram.handling.Handleable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class CallbackHandleable implements Handleable {

    @Autowired
    private GamesResponse gamesResponse;
    @Autowired
    private AgeResponse ageResponse;
    @Autowired
    private ProfileResponse profileResponse;
    @Autowired
    private BackResponse backResponse;
    @Autowired
    private EditProfileResponse editProfileResponse;

    @Override
    public void manage(Update update) {
        String callback = update.getCallbackQuery().getData();
        Message origin = update.getCallbackQuery().getMessage();
        if (callback.startsWith("profile")) {
            if (callback.startsWith("profile-games")) {
                gamesResponse.manage(callback, origin);
            } else if (callback.startsWith("profile-age")) {
                ageResponse.manage(callback, origin);
            }
        } else if (callback.startsWith("menu")) {
            if (callback.equals("menu-profile")){
                profileResponse.manage(callback, origin);
            } else if (callback.equals("menu-back")){
                backResponse.manage(callback, origin);
            } else if (callback.equals("menu-profile-edit")){
                editProfileResponse.manage(callback, origin);
            }
        }
    }

    @Override
    public boolean supports(Update update) {
        return update.getCallbackQuery() != null;
    }
}
