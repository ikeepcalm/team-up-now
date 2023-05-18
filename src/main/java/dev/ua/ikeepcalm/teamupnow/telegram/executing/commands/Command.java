package dev.ua.ikeepcalm.teamupnow.telegram.executing.commands;

import dev.ua.ikeepcalm.teamupnow.telegram.executing.Executable;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.ResourceBundle;

public abstract class Command extends Executable<Message> {
    public abstract void execute(Message origin);

    @Override
    public ResourceBundle getBundle(Message origin){
        if (origin.getFrom().getLanguageCode().equals("uk") || origin.getFrom().getLanguageCode().equals("ru")){
            return ResourceBundle.getBundle("i18n.messages_uk_UK");
        } else {
            return ResourceBundle.getBundle("i18n.messages_en_GB");
        }
    }
}
