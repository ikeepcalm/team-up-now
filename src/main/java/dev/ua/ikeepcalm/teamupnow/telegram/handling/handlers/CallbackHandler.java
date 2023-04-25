package dev.ua.ikeepcalm.teamupnow.telegram.handling.handlers;

import dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.discover.ExploreResponse;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.discover.MatchesResponse;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.init_profile.AgeResponse;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.init_profile.GamesResponse;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.menu.*;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.profile.EditProfileResponse;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.settings.SettingsDeleteResponse;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.settings.SettingsLangResponse;
import dev.ua.ikeepcalm.teamupnow.telegram.handling.Handleable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class CallbackHandler implements Handleable {

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
    @Autowired
    private SettingsResponse settingsResponse;
    @Autowired
    private MoreResponse moreResponse;
    @Autowired
    private SettingsLangResponse settingsLangResponse;
    @Autowired
    private SettingsDeleteResponse settingsDeleteResponse;
    @Autowired
    private DiscoverResponse discoverResponse;
    @Autowired
    private MatchesResponse matchesResponse;

    @Autowired
    private ConfigurableApplicationContext context;
    private final ConcurrentHashMap<Long, ExploreResponse> matchServiceMap = new ConcurrentHashMap<>();

    @Override
    public void manage(Update update) {
        String callback = update.getCallbackQuery().getData();
        Message origin = update.getCallbackQuery().getMessage();
        String callbackQueryId = update.getCallbackQuery().getId();
        if (callback.startsWith("profile")) {
            if (callback.startsWith("profile-games")) {
                gamesResponse.manage(callback, origin);
            } else if (callback.startsWith("profile-age")) {
                ageResponse.manage(callback, origin);
            }
        } else if (callback.startsWith("explore")) {
            if (callback.equals("explore-back")) {
                matchServiceMap.remove(origin.getChatId());
                discoverResponse.manage(callback, origin);
            } else {
                if (matchServiceMap.get(origin.getChatId()) == null) {
                    ExploreResponse exploreResponse = context.getBean(ExploreResponse.class);
                    exploreResponse.manage(callback, origin, callbackQueryId);
                    matchServiceMap.put(origin.getChatId(), exploreResponse);
                } else {
                    ExploreResponse exploreResponse = matchServiceMap.get((origin.getChatId()));
                    exploreResponse.manage(callback, origin, callbackQueryId);
                }
            }
        } else if (callback.startsWith("matches")){
            if (callback.equals("matches")){
                matchesResponse.manage(callback, origin, callbackQueryId);
            }
        } else if (callback.startsWith("menu")) {
            switch (callback) {
                case "menu-profile" -> profileResponse.manage(callback, origin);
                case "menu-profile-edit" -> editProfileResponse.manage(callback, origin);
                case "menu-settings" -> settingsResponse.manage(callback, origin);
                case "menu-settings-lang" -> settingsLangResponse.manage(callback, origin);
                case "menu-settings-delete-account" -> settingsDeleteResponse.manage(callback, origin);
                case "menu-more" -> moreResponse.manage(callback, origin);
                case "menu-back" -> backResponse.manage(callback, origin);
                case "menu-discover" -> discoverResponse.manage(callback, origin);
            }
        }
    }

    @Override
    public boolean supports(Update update) {
        return update.getCallbackQuery() != null;
    }
}
