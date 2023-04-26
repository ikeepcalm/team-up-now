package dev.ua.ikeepcalm.teamupnow.telegram.executing.commands;

import dev.ua.ikeepcalm.teamupnow.telegram.executing.Executable;
import org.telegram.telegrambots.meta.api.objects.Message;

public abstract class Command extends Executable {
    public abstract void execute(Message origin);

}
