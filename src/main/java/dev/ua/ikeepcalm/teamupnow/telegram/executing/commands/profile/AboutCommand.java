package dev.ua.ikeepcalm.teamupnow.telegram.executing.commands.profile;

import dev.ua.ikeepcalm.teamupnow.aop.annotations.Progressable;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.ProgressENUM;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.commands.Command;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.MultiMessage;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.ResourceBundle;

@Component
public class AboutCommand extends Command {

    @Override
    @Progressable(ProgressENUM.ABOUT)
    public void execute(Message origin) {
        ResourceBundle locale = getBundle(origin);
        MultiMessage message = new MultiMessage();
        message.setText(locale.getString("profile-description"));
        message.setChatId(origin.getChatId());
        telegramService.sendMultiMessage(message);
    }
}
