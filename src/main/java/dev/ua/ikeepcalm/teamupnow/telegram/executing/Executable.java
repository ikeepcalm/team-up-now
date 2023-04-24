package dev.ua.ikeepcalm.teamupnow.telegram.executing;

import org.telegram.telegrambots.meta.api.objects.Message;

public interface Executable {

    default void manage(String receivedCallback, Message origin){};
    default void manage(String receivedCallback, Message origin, String callbackQueryId){};
    default void execute(Message origin){};

}
