package dev.ua.ikeepcalm.teamupnow.telegram;

import dev.ua.ikeepcalm.teamupnow.telegram.handling.Handleable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
public class BotInstance extends TelegramLongPollingBot {

    @Autowired
    private List<Handleable> handleableList;

    @Override
    public void onUpdateReceived(Update update) {
        for (Handleable h : handleableList) {
            if (h.supports(update)){
                h.manage(update);
            }
        }
    }

    //TODO: Integrate this value with Docker ENV
    public BotInstance() {
        super("6286513115:AAEEJSBflwdRuGTq7FpR4olumTEszh2AIa4");
    }

    //TODO: Integrate this value with Docker ENV
    @Override
    public String getBotUsername() {
        return "teamupnow_bot";
    }
}
