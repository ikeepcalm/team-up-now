package dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.settings;

import dev.ua.ikeepcalm.teamupnow.aop.annotations.I18N;
import dev.ua.ikeepcalm.teamupnow.database.dao.service.impls.CredentialsService;
import dev.ua.ikeepcalm.teamupnow.database.entities.Credentials;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.LanguageENUM;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.Executable;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.menu.SettingsResponse;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.implementations.LocaleTool;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class SettingsLangResponse implements Executable {

    @Autowired
    private SettingsResponse settingsResponse;
    @Autowired
    private CredentialsService credentialsService;
    private LocaleTool locale;

    @I18N
    @Override
    @Transactional
    public void manage(String receivedCallback, Message origin) {
        Credentials credentials = credentialsService.findByAccountId(origin.getChatId());
        if (credentials.getUiLanguage() == LanguageENUM.ENGLISH){
            credentials.setUiLanguage(LanguageENUM.UKRAINIAN);
        } else if (credentials.getUiLanguage() == LanguageENUM.UKRAINIAN){
            credentials.setUiLanguage(LanguageENUM.ENGLISH);
        } credentialsService.save(credentials);
        settingsResponse.manage(receivedCallback, origin);
    }
}
