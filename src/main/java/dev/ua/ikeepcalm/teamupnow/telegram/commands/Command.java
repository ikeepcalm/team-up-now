package dev.ua.ikeepcalm.teamupnow.telegram.commands;

import dev.ua.ikeepcalm.teamupnow.telegram.services.TelegramService;
import dev.ua.ikeepcalm.teamupnow.telegram.services.impls.TelegramServiceExecutor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static dev.ua.ikeepcalm.teamupnow.Application.context;

public interface Command {

    TelegramService telegramService = context.getBean(TelegramServiceExecutor.class);

}
