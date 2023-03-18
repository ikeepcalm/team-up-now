package dev.ua.ikeepcalm.teamupnow.telegram.executing.responses;

import dev.ua.ikeepcalm.teamupnow.aop.annotations.Progressable;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.ProgressENUM;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.services.TelegramService;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.services.implementations.TelegramServiceExecutor;
import dev.ua.ikeepcalm.teamupnow.telegram.proxies.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class AboutResponse {
    @Autowired
    private TelegramService telegramService;

    @Progressable(ProgressENUM.ABOUT)
    public void execute(Update update) {
        Message message = new Message();
        message.setText("Now, send a message with a small description of yourself that you want other users to see." +
                " For example: \"Looking for friendly CS:GO players, just to play in the evenings in cosy company <3\".");
        message.setChatId(update.getMessage().getChatId());
        telegramService.sendMessage(message);
    }
}
