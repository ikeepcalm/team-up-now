package dev.ua.ikeepcalm.teamupnow.telegram.executing.commands.system;

import dev.ua.ikeepcalm.teamupnow.database.dao.service.impls.CredentialsService;
import dev.ua.ikeepcalm.teamupnow.database.entities.Credentials;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.commands.Command;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.TelegramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

@Component
public class NotifyCommand extends Command {

    @Autowired
    private TelegramService telegramService;
    @Autowired
    private CredentialsService credentialsService;

    @Override
    public void execute(Message origin) {
        if (origin.getChatId() == 586319182) {
            List<Credentials> credentialsList = credentialsService.findAll();
            for (Credentials credentials : credentialsList){
                telegramService.sendForwardMessage(origin.getReplyToMessage(), credentials.getAccountId());
            }
        }
    }
}
