package dev.ua.ikeepcalm.teamupnow.telegram.handling.handlers;

import dev.ua.ikeepcalm.teamupnow.aop.annotations.Progressable;
import dev.ua.ikeepcalm.teamupnow.database.dao.service.impls.CredentialsService;
import dev.ua.ikeepcalm.teamupnow.database.entities.Credentials;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.ProgressENUM;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.messages.BonusMessage;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.messages.init_profile.AboutMessage;
import dev.ua.ikeepcalm.teamupnow.telegram.handling.Handleable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class MessageHandler implements Handleable {

    private final AboutMessage aboutMessage;
    private final BonusMessage bonusMessage;
    private final CredentialsService credentialsService;

    @Autowired
    public MessageHandler(AboutMessage aboutMessage, BonusMessage bonusMessage, CredentialsService credentialsService) {
        this.aboutMessage = aboutMessage;
        this.bonusMessage = bonusMessage;
        this.credentialsService = credentialsService;
    }

    @Override
    @Progressable(ProgressENUM.ABOUT)
    public void manage(Update update) {
        Message origin = update.getMessage();
        Credentials user = credentialsService.findByAccountId(origin.getChatId());
        if (user.getProgress().getProgressENUM() == ProgressENUM.ABOUT){
            aboutMessage.execute(origin);
        } else if(user.getProgress().getProgressENUM() == ProgressENUM.BONUS){
            bonusMessage.execute(origin);
        }
    }

    @Override
    public boolean supports(Update update) {
        if (update.getMessage() != null){
            return !update.getMessage().getText().startsWith("/");
        } else {
            return false;
        }
    }
}