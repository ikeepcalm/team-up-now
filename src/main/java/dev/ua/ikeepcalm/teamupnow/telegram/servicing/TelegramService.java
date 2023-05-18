package dev.ua.ikeepcalm.teamupnow.telegram.servicing;


import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.AlterMessage;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.MultiMessage;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.PurgeMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface TelegramService{

    void sendMultiMessage(MultiMessage multiMessage);

    void sendAnswerCallbackQuery(String text, String callbackQueryId);

    void sendForwardMessage(Message origin, long chatId);

    void sendAlterMessage(AlterMessage alterMessage);

    void sendPurgeMessage(PurgeMessage purgeMessage);
}
