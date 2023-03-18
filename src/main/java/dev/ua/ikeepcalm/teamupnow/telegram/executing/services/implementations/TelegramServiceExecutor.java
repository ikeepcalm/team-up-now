package dev.ua.ikeepcalm.teamupnow.telegram.executing.services.implementations;

import dev.ua.ikeepcalm.teamupnow.telegram.executing.services.TelegramService;
import dev.ua.ikeepcalm.teamupnow.telegram.proxies.Callback;
import dev.ua.ikeepcalm.teamupnow.telegram.proxies.EditMessage;
import dev.ua.ikeepcalm.teamupnow.telegram.proxies.Message;
import dev.ua.ikeepcalm.teamupnow.telegram.proxies.ToDelete;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class TelegramServiceExecutor extends DefaultAbsSender implements TelegramService {

    //TODO: Use Docker ENV variable here
    public TelegramServiceExecutor() {
        super(new DefaultBotOptions(), "6286513115:AAEEJSBflwdRuGTq7FpR4olumTEszh2AIa4");
    }

    @Override
    public void sendMessage(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(message.getText());
        sendMessage.setChatId(message.getChatId());
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException("Failed to execute message: " + message.toString(), e);
        }
    }

    @Override
    public void sendEditMessage(EditMessage editMessage) {
        EditMessageReplyMarkup editMessageReplyMarkup = new EditMessageReplyMarkup();
        editMessageReplyMarkup.setMessageId(editMessage.getMessageId());
        editMessageReplyMarkup.setChatId(editMessage.getChatId());
        editMessageReplyMarkup.setReplyMarkup(editMessage.getReplyKeyboard());
        try {
            execute(editMessageReplyMarkup);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendCallback(Callback callback) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(callback.getText());
        sendMessage.setChatId(callback.getChatId());
        sendMessage.setReplyMarkup(callback.getReplyKeyboard());
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException("Failed to execute callback: " + callback.toString(), e);
        }
    }

    @Override
    public void deleteCallback(ToDelete toDelete) {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(toDelete.getChatId());
        deleteMessage.setMessageId(toDelete.getMessageId());
        try {
            execute(deleteMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException("Failed to delete callback: " + deleteMessage.toString(), e);
        }
    }
}
