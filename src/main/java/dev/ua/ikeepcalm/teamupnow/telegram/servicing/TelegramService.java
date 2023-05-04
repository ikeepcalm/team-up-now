package dev.ua.ikeepcalm.teamupnow.telegram.servicing;


import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.AlterMessage;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.MultiMessage;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.PurgeMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface TelegramService{

    Message sendMultiMessage(MultiMessage multiMessage);

    void sendAnswerCallbackQuery(String text, String callbackQueryId);

    void sendAlterMessage(AlterMessage alterMessage);

    void sendPurgeMessage(PurgeMessage purgeMessage);
}
