package dev.ua.ikeepcalm.teamupnow.telegram.executing;

import org.telegram.telegrambots.meta.api.objects.Message;

public interface Executable {

    default void manage(String receivedCallback, Message origin){};
    default void execute(Message origin){};

}
