package dev.ua.ikeepcalm.teamupnow.telegram.servicing.mediators;

import dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.discover.MatchesResponse;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.init_profile.AgeResponse;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.init_profile.GamesResponse;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.menu.*;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.profile.EditProfileResponse;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.settings.SettingsDeleteResponse;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.settings.SettingsLangResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class ResponseMediator {
    private final MatchesResponse matchesResponse;
    private final AgeResponse ageResponse;
    private final GamesResponse gamesResponse;
    private final BackResponse backResponse;
    private final DiscoverResponse discoverResponse;
    private final MoreResponse moreResponse;
    private final ProfileResponse profileResponse;
    private final SettingsResponse settingsResponse;
    private final EditProfileResponse editProfileResponse;
    private final SettingsDeleteResponse settingsDeleteResponse;
    private final SettingsLangResponse settingsLangResponse;

    public void executeMatchesResponse(String receivedCallback, Message origin, String callbackQueryId) {
        matchesResponse.manage(receivedCallback, origin, callbackQueryId);
    }

    public void executeAgeResponse(String receivedCallback, Message origin) {
        ageResponse.manage(receivedCallback, origin);
    }

    public void executeGamesResponse(String receivedCallback, Message origin) {
        gamesResponse.manage(receivedCallback, origin);
    }

    public void executeBackResponse(String receivedCallback, Message origin) {
        backResponse.manage(receivedCallback, origin);
    }

    public void executeDiscoverResponse(String receivedCallback, Message origin) {
        discoverResponse.manage(receivedCallback, origin);
    }

    public void executeMoreResponse(String receivedCallback, Message origin) {
        moreResponse.manage(receivedCallback, origin);
    }

    public void executeProfileResponse(String receivedCallback, Message origin) {
        profileResponse.manage(receivedCallback, origin);
    }

    public void executeSettingsResponse(String receivedCallback, Message origin) {
        settingsResponse.manage(receivedCallback, origin);
    }

    public void executeEditProfileResponse(String receivedCallback, Message origin) {
        editProfileResponse.manage(receivedCallback, origin);
    }

    public void executeSettingsDeleteResponse(String receivedCallback, Message origin) {
        settingsDeleteResponse.manage(receivedCallback, origin);
    }

    public void executeSettingsLangResponse(String receivedCallback, Message origin) {
        settingsLangResponse.manage(receivedCallback, origin);
    }

    @Autowired
    public ResponseMediator(
            MatchesResponse matchesResponse,
            AgeResponse ageResponse,
            GamesResponse gamesResponse,
            BackResponse backResponse,
            DiscoverResponse discoverResponse,
            MoreResponse moreResponse,
            ProfileResponse profileResponse,
            SettingsResponse settingsResponse,
            EditProfileResponse editProfileResponse,
            SettingsDeleteResponse settingsDeleteResponse,
            SettingsLangResponse settingsLangResponse) {
        this.matchesResponse = matchesResponse;
        this.ageResponse = ageResponse;
        this.gamesResponse = gamesResponse;
        this.backResponse = backResponse;
        this.discoverResponse = discoverResponse;
        this.moreResponse = moreResponse;
        this.profileResponse = profileResponse;
        this.settingsResponse = settingsResponse;
        this.editProfileResponse = editProfileResponse;
        this.settingsDeleteResponse = settingsDeleteResponse;
        this.settingsLangResponse = settingsLangResponse;
    }
}
