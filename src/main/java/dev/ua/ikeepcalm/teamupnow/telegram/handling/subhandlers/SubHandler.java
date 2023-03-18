package dev.ua.ikeepcalm.teamupnow.telegram.handling.subhandlers;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface SubHandler {
    void manage(Update update);
}
