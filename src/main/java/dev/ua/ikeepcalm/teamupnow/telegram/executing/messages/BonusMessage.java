package dev.ua.ikeepcalm.teamupnow.telegram.executing.messages;

import dev.ua.ikeepcalm.teamupnow.database.dao.service.impls.BonusService;
import dev.ua.ikeepcalm.teamupnow.database.entities.Credentials;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.ProgressENUM;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.mediators.CommandMediator;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.MultiMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ResourceBundle;

@Component
public class BonusMessage extends Message {

    private final BonusService bonusService;
    private final CommandMediator commandMediator;

    @Autowired
    public BonusMessage(BonusService bonusService, CommandMediator commandMediator) {
        this.bonusService = bonusService;
        this.commandMediator = commandMediator;
    }


    @Override
    public void execute(org.telegram.telegrambots.meta.api.objects.Message origin) {
        String code = origin.getText();
        ResourceBundle locale = getBundle(origin);
        Credentials credentials = credentialsService.findByAccountId(origin.getChatId());
        if (bonusService.validate(code)){
            credentials.setSustainableTokens(credentials.getSustainableTokens()+1);
            credentials.getProgress().setProgressENUM(ProgressENUM.DONE);
            credentialsService.save(credentials);
            MultiMessage multiMessage = new MultiMessage();
            multiMessage.setChatId(origin.getChatId());
            multiMessage.setText(locale.getString("bonus-success"));
            telegramService.sendMultiMessage(multiMessage);
        } else {
            credentials.getProgress().setProgressENUM(ProgressENUM.DONE);
            credentialsService.save(credentials);
            MultiMessage multiMessage = new MultiMessage();
            multiMessage.setChatId(origin.getChatId());
            multiMessage.setText(locale.getString("bonus-denied"));
            telegramService.sendMultiMessage(multiMessage);
        } commandMediator.executeMenuCommand(origin);
    }
}
