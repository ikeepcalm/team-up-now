package dev.ua.ikeepcalm.teamupnow.telegram.handling.handlers;

import dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.discover.ExploreResponse;
import dev.ua.ikeepcalm.teamupnow.telegram.handling.Handleable;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.mediators.ResponseMediator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class CallbackHandler implements Handleable {

    @Autowired
    private ResponseMediator responseMediator;
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
                responseMediator.executeGamesResponse(callback, origin);
            } else if (callback.startsWith("profile-age")) {
                responseMediator.executeAgeResponse(callback, origin);
            }
        } else if (callback.startsWith("explore")) {
            if (callback.equals("explore-back")) {
                matchServiceMap.remove(origin.getChatId());
                responseMediator.executeDiscoverResponse(callback, origin);
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
                responseMediator.executeMatchesResponse(callback, origin, callbackQueryId);
            }
        } else if (callback.startsWith("menu")) {
            switch (callback) {
                case "menu-profile" -> responseMediator.executeProfileResponse(callback, origin);
                case "menu-profile-edit" -> responseMediator.executeEditProfileResponse(callback, origin);
                case "menu-settings" -> responseMediator.executeSettingsResponse(callback, origin);
                case "menu-settings-lang" -> responseMediator.executeSettingsLangResponse(callback, origin);
                case "menu-settings-delete-account" -> responseMediator.executeSettingsDeleteResponse(callback, origin);
                case "menu-more" -> responseMediator.executeMoreResponse(callback, origin);
                case "menu-back" -> responseMediator.executeBackResponse(callback, origin);
                case "menu-discover" -> responseMediator.executeDiscoverResponse(callback, origin);
            }
        }
    }

    @Override
    public boolean supports(Update update) {
        return update.getCallbackQuery() != null;
    }
}
