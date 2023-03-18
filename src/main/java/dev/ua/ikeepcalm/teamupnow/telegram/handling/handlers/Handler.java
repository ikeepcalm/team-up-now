package dev.ua.ikeepcalm.teamupnow.telegram.handling.handlers;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface Handler {
    void manage(Update update);
    boolean supports(Update update);
}
