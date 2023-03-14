package dev.ua.ikeepcalm.teamupnow.telegram.services.impls;

import dev.ua.ikeepcalm.teamupnow.telegram.models.Callback;
import dev.ua.ikeepcalm.teamupnow.telegram.models.EditMessage;
import dev.ua.ikeepcalm.teamupnow.telegram.models.Message;
import dev.ua.ikeepcalm.teamupnow.telegram.models.ToDelete;
import dev.ua.ikeepcalm.teamupnow.telegram.services.TelegramService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class TelegramServiceExecutor extends DefaultAbsSender implements TelegramService {

    //TODO: Make bot token not hard-coded, somehow via Spring @Value
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
