package dev.ua.ikeepcalm.teamupnow.telegram.handlers.subhandlers;

import dev.ua.ikeepcalm.teamupnow.telegram.services.TelegramService;
import dev.ua.ikeepcalm.teamupnow.telegram.services.impls.TelegramServiceExecutor;
import org.telegram.telegrambots.meta.api.objects.Update;

import static dev.ua.ikeepcalm.teamupnow.Application.context;

public interface SubHandler {
    TelegramService telegramService = context.getBean(TelegramServiceExecutor.class);
    void manage(Update update);
}
