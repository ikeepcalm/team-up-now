package dev.ua.ikeepcalm.teamupnow.telegram.executing.messages;

import dev.ua.ikeepcalm.teamupnow.telegram.executing.Executable;

public abstract class Message extends Executable {
    public abstract void execute(org.telegram.telegrambots.meta.api.objects.Message origin);
}
