package dev.ua.ikeepcalm.teamupnow.telegram.handlers;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface Handler {
    void manage(Update update);
    boolean supports(Update update);
}
