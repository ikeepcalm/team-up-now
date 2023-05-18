package dev.ua.ikeepcalm.teamupnow.telegram.servicing.mediators;

import dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.init_profile.AgeResponse;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.menu.*;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.profile.EditAboutResponse;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.profile.EditAgeResponse;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.profile.EditBackResponse;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.profile.EditProfileResponse;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.settings.SettingsDeleteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Component
public class ResponseMediator {
    private final AgeResponse ageResponse;
    private final BackResponse backResponse;
    private final DiscoverResponse discoverResponse;
    private final MoreResponse moreResponse;
    private final ProfileResponse profileResponse;
    private final SettingsResponse settingsResponse;
    private final EditProfileResponse editProfileResponse;
    private final EditAgeResponse editAgeResponse;
    private final EditBackResponse editBackResponse;
    private final EditAboutResponse editAboutResponse;
    private final SettingsDeleteResponse settingsDeleteResponse;

    public void executeAgeResponse(String receivedCallback, CallbackQuery origin) {
        ageResponse.manage(receivedCallback, origin);
    }

    public void executeBackResponse(String receivedCallback, CallbackQuery origin) {
        backResponse.manage(receivedCallback, origin);
    }

    public void executeDiscoverResponse(String receivedCallback, CallbackQuery origin) {
        discoverResponse.manage(receivedCallback, origin);
    }

    public void executeMoreResponse(String receivedCallback, CallbackQuery origin) {
        moreResponse.manage(receivedCallback, origin);
    }

    public void executeProfileResponse(String receivedCallback, CallbackQuery origin) {
        profileResponse.manage(receivedCallback, origin);
    }

    public void executeSettingsResponse(String receivedCallback, CallbackQuery origin) {
        settingsResponse.manage(receivedCallback, origin);
    }

    public void executeEditProfileResponse(String receivedCallback, CallbackQuery origin) {
        editProfileResponse.manage(receivedCallback, origin);
    }

    public void executeEditBackResponse(String receivedCallback, CallbackQuery origin) {
        editBackResponse.manage(receivedCallback, origin);
    }

    public void executeEditAboutResponse(String receivedCallback, CallbackQuery origin) {
        editAboutResponse.manage(receivedCallback, origin);
    }

    public void executeSettingsDeleteResponse(String receivedCallback, CallbackQuery origin) {
        settingsDeleteResponse.manage(receivedCallback, origin);
    }

    public void executeEditAgeResponse(String receivedCallback, CallbackQuery origin) {
        editAgeResponse.manage(receivedCallback, origin);
    }

    @Autowired
    public ResponseMediator(
            AgeResponse ageResponse,
            BackResponse backResponse,
            DiscoverResponse discoverResponse,
            MoreResponse moreResponse,
            ProfileResponse profileResponse,
            SettingsResponse settingsResponse,
            EditProfileResponse editProfileResponse,
            EditAgeResponse editAgeResponse, EditBackResponse editBackResponse, EditAboutResponse editAboutResponse, SettingsDeleteResponse settingsDeleteResponse) {
        this.ageResponse = ageResponse;
        this.backResponse = backResponse;
        this.discoverResponse = discoverResponse;
        this.moreResponse = moreResponse;
        this.profileResponse = profileResponse;
        this.settingsResponse = settingsResponse;
        this.editProfileResponse = editProfileResponse;
        this.editAgeResponse = editAgeResponse;
        this.editBackResponse = editBackResponse;
        this.editAboutResponse = editAboutResponse;
        this.settingsDeleteResponse = settingsDeleteResponse;
    }
}
