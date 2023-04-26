package dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks;

import dev.ua.ikeepcalm.teamupnow.telegram.executing.Executable;
import org.telegram.telegrambots.meta.api.objects.Message;

public abstract class QueryCallback extends Executable {

    public abstract void manage(String receivedCallback, Message origin, String callbackQueryId);
}
