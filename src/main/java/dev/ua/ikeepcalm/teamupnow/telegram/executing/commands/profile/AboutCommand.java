package dev.ua.ikeepcalm.teamupnow.telegram.executing.commands.profile;

import dev.ua.ikeepcalm.teamupnow.aop.annotations.ClearKeyboard;
import dev.ua.ikeepcalm.teamupnow.aop.annotations.I18N;
import dev.ua.ikeepcalm.teamupnow.aop.annotations.Progressable;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.ProgressENUM;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.commands.Command;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.MultiMessage;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.tools.LocaleTool;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class AboutCommand extends Command {
    private LocaleTool locale;

    @I18N
    @Override
    @ClearKeyboard
    @Progressable(ProgressENUM.ABOUT)
    public void execute(Message origin) {
        MultiMessage message = new MultiMessage();
        message.setText(locale.getMessage("profile-description"));
        message.setChatId(origin.getChatId());
        telegramService.sendMultiMessage(message);
    }
}
