package dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks;

import dev.ua.ikeepcalm.teamupnow.telegram.executing.Executable;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.ResourceBundle;

public abstract class SimpleCallback extends Executable<CallbackQuery> {
    public abstract void manage(String receivedCallback, CallbackQuery origin);

    @Override
    public ResourceBundle getBundle(CallbackQuery origin){
        if (origin.getFrom().getLanguageCode().equals("uk") || origin.getFrom().getLanguageCode().equals("ru")){
            return ResourceBundle.getBundle("i18n.messages_uk_UK");
        } else {
            return ResourceBundle.getBundle("i18n.messages_en_GB");
        }
    }
}
