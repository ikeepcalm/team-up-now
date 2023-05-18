package dev.ua.ikeepcalm.teamupnow.telegram.handling.handlers;

import dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.discover.ExploreResponse;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.discover.MatchesResponse;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.init_profile.GamesResponse;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.profile.EditGamesResponse;
import dev.ua.ikeepcalm.teamupnow.telegram.handling.Handleable;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.mediators.ResponseMediator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class CallbackHandler implements Handleable {

    private final ResponseMediator responseMediator;
    private final ConfigurableApplicationContext context;
    private final ConcurrentHashMap<Long, ExploreResponse> exploreResponseMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Long, GamesResponse> gamesResponseMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Long, EditGamesResponse> editGamesManagerMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Long, MatchesResponse> matchesResponseMap = new ConcurrentHashMap<>();

    @Autowired
    public CallbackHandler(ResponseMediator responseMediator, ConfigurableApplicationContext context) {
        this.responseMediator = responseMediator;
        this.context = context;
    }

    @Override
    public void manage(Update update) {
        String callback = update.getCallbackQuery().getData();
        CallbackQuery origin = update.getCallbackQuery();
        String callbackQueryId = update.getCallbackQuery().getId();
        if (callback.startsWith("profile")) {
            if (callback.startsWith("profile-games")) {
                if (callback.equals("profile-games-ready")) {
                    if (gamesResponseMap.get(origin.getMessage().getChatId()) == null) {
                        GamesResponse gamesResponse = context.getBean(GamesResponse.class);
                        gamesResponse.manage(callback, origin);
                    } else {
                        gamesResponseMap.get(origin.getMessage().getChatId()).manage(callback, origin);
                        gamesResponseMap.remove(origin.getMessage().getChatId());
                    }
                } else {
                    if (gamesResponseMap.get(origin.getMessage().getChatId()) == null) {
                        GamesResponse gamesResponse = context.getBean(GamesResponse.class);
                        gamesResponseMap.put(origin.getMessage().getChatId(), gamesResponse);
                        gamesResponse.manage(callback, origin);
                    } else {
                        gamesResponseMap.get(origin.getMessage().getChatId()).manage(callback, origin);
                    }
                }
            } else if (callback.startsWith("profile-age")) {
                responseMediator.executeAgeResponse(callback, origin);
            }
        } else if (callback.startsWith("explore")) {
            if (callback.equals("explore-back")) {
                exploreResponseMap.remove(origin.getMessage().getChatId());
                responseMediator.executeDiscoverResponse(callback, origin);
            } else {
                if (exploreResponseMap.get(origin.getMessage().getChatId()) == null) {
                    ExploreResponse exploreResponse = context.getBean(ExploreResponse.class);
                    exploreResponse.manage(callback, origin, callbackQueryId);
                    exploreResponseMap.put(origin.getMessage().getChatId(), exploreResponse);
                } else {
                    ExploreResponse exploreResponse = exploreResponseMap.get((origin.getMessage().getChatId()));
                    exploreResponse.manage(callback, origin, callbackQueryId);
                }
            }
        } else if (callback.startsWith("matches")) {
            if (callback.equals("matches-back")) {
                matchesResponseMap.remove(origin.getMessage().getChatId());
                responseMediator.executeDiscoverResponse(callback, origin);
            } else {
                if (matchesResponseMap.get(origin.getMessage().getChatId()) == null) {
                    MatchesResponse matchesResponse = context.getBean(MatchesResponse.class);
                    matchesResponse.manage(callback, origin, callbackQueryId);
                    matchesResponseMap.put(origin.getMessage().getChatId(), matchesResponse);
                } else {
                    MatchesResponse matchesResponse = matchesResponseMap.get((origin.getMessage().getChatId()));
                    matchesResponse.manage(callback, origin, callbackQueryId);
                }
            }
        } else if (callback.startsWith("menu")) {
            switch (callback) {
                case "menu-profile" -> responseMediator.executeProfileResponse(callback, origin);
                case "menu-profile-edit" -> responseMediator.executeEditProfileResponse(callback, origin);
                case "menu-settings" -> responseMediator.executeSettingsResponse(callback, origin);
                case "menu-settings-delete-account" -> responseMediator.executeSettingsDeleteResponse(callback, origin);
                case "menu-more" -> responseMediator.executeMoreResponse(callback, origin);
                case "menu-back" -> responseMediator.executeBackResponse(callback, origin);
                case "menu-discover" -> responseMediator.executeDiscoverResponse(callback, origin);
            }
        } else if (callback.startsWith("edit-profile")) {
            if (callback.startsWith("edit-profile-games")) {
                if (callback.equals("edit-profile-games-ready")) {
                    if (editGamesManagerMap.get(origin.getMessage().getChatId()) == null) {
                        EditGamesResponse editGamesResponse = context.getBean(EditGamesResponse.class);
                        editGamesResponse.manage(callback, origin);
                    } else {
                        editGamesManagerMap.get(origin.getMessage().getChatId()).manage(callback, origin);
                        editGamesManagerMap.remove(origin.getMessage().getChatId());
                    }
                } else {
                    if (editGamesManagerMap.get(origin.getMessage().getChatId()) == null) {
                        EditGamesResponse editGamesResponse = context.getBean(EditGamesResponse.class);
                        editGamesManagerMap.put(origin.getMessage().getChatId(), editGamesResponse);
                        editGamesResponse.manage(callback, origin);
                    } else {
                        editGamesManagerMap.get(origin.getMessage().getChatId())
                                .manage(callback, origin);
                    }
                }
            } else if (callback.startsWith("edit-profile-age")) {
                responseMediator.executeEditAgeResponse(callback, origin);
            } else if (callback.startsWith("edit-profile-about")) {
                responseMediator.executeEditAboutResponse(callback, origin);
            } else if (callback.equals("edit-profile-back")) {
                responseMediator.executeEditBackResponse(callback, origin);
            }
        }
    }

    @Override
    public boolean supports(Update update) {
        return update.getCallbackQuery() != null;
    }
}
