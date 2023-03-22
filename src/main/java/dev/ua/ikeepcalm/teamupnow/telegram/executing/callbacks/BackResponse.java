package dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks;

import dev.ua.ikeepcalm.teamupnow.telegram.executing.Executable;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.commands.MenuCommand;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.TelegramService;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.PurgeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class BackResponse implements Executable {
    @Autowired
    private TelegramService telegramService;

    @Autowired
    private MenuCommand menuCommand;

    @Override
    public void manage(String receivedCallback, Message origin) {
        {
            PurgeMessage purgeMessage = new PurgeMessage(origin.getMessageId(), origin.getChatId());
            telegramService.deleteMessage(purgeMessage);
        } menuCommand.execute(origin);
    }
}
