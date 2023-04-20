package dev.ua.ikeepcalm.teamupnow.telegram.servicing.implementations;

import dev.ua.ikeepcalm.teamupnow.telegram.servicing.TelegramService;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.AlterMessage;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.MediaMessage;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.MultiMessage;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.PurgeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageCaption;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;

@Service
@PropertySource("classpath:credentials.properties")
public class TelegramServiceExecutor extends DefaultAbsSender implements TelegramService {

    //TODO: Use Docker ENV variable here
    public TelegramServiceExecutor(@Value("${telegram.bot.token}") String botToken) {
        super(new DefaultBotOptions(), botToken);
    }

    @Override
    public void sendAlterMessage(AlterMessage alterMessage) {
        try {
            EditMessageCaption editMessageCaption = new EditMessageCaption();
            editMessageCaption.setMessageId(alterMessage.getMessageId());
            editMessageCaption.setCaption(alterMessage.getText());
            editMessageCaption.setReplyMarkup((InlineKeyboardMarkup) alterMessage.getReplyKeyboard());
            editMessageCaption.setChatId(alterMessage.getChatId());
            EditMessageMedia editMessageMedia = new EditMessageMedia();
            editMessageMedia.setMessageId(alterMessage.getMessageId());
            editMessageMedia.setChatId(alterMessage.getChatId());
            editMessageMedia.setMedia(new InputMediaPhoto(alterMessage.getFileURL()));
            execute(editMessageMedia);
            execute(editMessageCaption);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendAlterMessage(MultiMessage multiMessage){
        EditMessageReplyMarkup editMessageReplyMarkup = new EditMessageReplyMarkup();
        editMessageReplyMarkup.setMessageId(multiMessage.getMessageId());
        editMessageReplyMarkup.setChatId(multiMessage.getChatId());
        editMessageReplyMarkup.setReplyMarkup((InlineKeyboardMarkup) multiMessage.getReplyKeyboard());
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
    public void sendPurgeMessage(PurgeMessage purgeMessage) {
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
        }
        if (message.getReplyKeyboard() != null) {
            sendPhoto.setReplyMarkup(message.getReplyKeyboard());
        }
        try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
