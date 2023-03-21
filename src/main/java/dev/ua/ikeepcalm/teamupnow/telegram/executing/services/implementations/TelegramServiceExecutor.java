package dev.ua.ikeepcalm.teamupnow.telegram.executing.services.implementations;

import dev.ua.ikeepcalm.teamupnow.telegram.executing.services.TelegramService;
import dev.ua.ikeepcalm.teamupnow.telegram.proxies.MediaMessage;
import dev.ua.ikeepcalm.teamupnow.telegram.proxies.MultiMessage;
import dev.ua.ikeepcalm.teamupnow.telegram.proxies.AlterMessage;
import dev.ua.ikeepcalm.teamupnow.telegram.proxies.PurgeMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Service
public class TelegramServiceExecutor extends DefaultAbsSender implements TelegramService {

    //TODO: Use Docker ENV variable here
    public TelegramServiceExecutor() {
        super(new DefaultBotOptions(), "6286513115:AAEEJSBflwdRuGTq7FpR4olumTEszh2AIa4");
    }

    @Override
    public void sendEditMessage(AlterMessage alterMessage) {
        EditMessageReplyMarkup editMessageReplyMarkup = new EditMessageReplyMarkup();
        editMessageReplyMarkup.setMessageId(alterMessage.getMessageId());
        editMessageReplyMarkup.setChatId(alterMessage.getChatId());
        editMessageReplyMarkup.setReplyMarkup(alterMessage.getReplyKeyboard());
        try {
            execute(editMessageReplyMarkup);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Message sendMultiMessage(MultiMessage multiMessage) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(multiMessage.getText());
        sendMessage.setChatId(multiMessage.getChatId());
        sendMessage.setReplyMarkup(multiMessage.getReplyKeyboard());
        try {
            return execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException("Failed to execute callback: " + multiMessage.toString(), e);
        }
    }

    @Override
    public void deleteMessage(PurgeMessage purgeMessage) {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(purgeMessage.getChatId());
        deleteMessage.setMessageId(purgeMessage.getMessageId());
        try {
            execute(deleteMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException("Failed to delete callback: " + deleteMessage.toString(), e);
        }
    }

    @Override
    public void sendMediaMessage(MediaMessage message) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(message.getChatId());
        sendPhoto.setPhoto(new InputFile(new File(message.getFilePath())));
        if (message.getText() != null) {
            sendPhoto.setCaption(message.getText());
        } if (message.getReplyKeyboard() != null){
            sendPhoto.setReplyMarkup(message.getReplyKeyboard());
        } try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
