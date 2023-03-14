package dev.ua.ikeepcalm.teamupnow.telegram.core;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import static dev.ua.ikeepcalm.teamupnow.Application.context;


public class BotInstance extends TelegramLongPollingBot {

    private final AssignNode assignNode = context.getBean(AssignNode.class);

    @Override
    public void onUpdateReceived(Update update) {
        if (assignNode.supports(update)) {
            assignNode.manage(update);
        }
    }

    //TODO: Make bot token not hard-coded, somehow via Spring @Value
    public BotInstance() {
        super("6286513115:AAEEJSBflwdRuGTq7FpR4olumTEszh2AIa4");
    }

    @Override
    public String getBotUsername() {
        return "teamupnow_bot";
    }
}
