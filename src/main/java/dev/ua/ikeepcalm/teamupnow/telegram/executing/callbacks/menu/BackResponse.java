package dev.ua.ikeepcalm.teamupnow.telegram.executing.callbacks.menu;

import dev.ua.ikeepcalm.teamupnow.aop.annotations.Sequenced;
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
    @Sequenced
    public void manage(String receivedCallback, Message origin) {menuCommand.execute(origin);
    }
}
