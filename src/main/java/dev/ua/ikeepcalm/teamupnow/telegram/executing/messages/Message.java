package dev.ua.ikeepcalm.teamupnow.telegram.executing.messages;

import dev.ua.ikeepcalm.teamupnow.telegram.executing.Executable;

import java.util.ResourceBundle;

public abstract class Message extends Executable<org.telegram.telegrambots.meta.api.objects.Message> {
    public abstract void execute(org.telegram.telegrambots.meta.api.objects.Message origin);

    @Override
    public ResourceBundle getBundle(org.telegram.telegrambots.meta.api.objects.Message origin){
        if (origin.getFrom().getLanguageCode().equals("uk") || origin.getFrom().getLanguageCode().equals("ru")){
            return ResourceBundle.getBundle("i18n.messages_uk_UK");
        } else {
            return ResourceBundle.getBundle("i18n.messages_en_GB");
        }
    }
}
