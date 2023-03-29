package dev.ua.ikeepcalm.teamupnow.telegram.servicing;


import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.MediaMessage;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.MultiMessage;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.AlterMessage;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.PurgeMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface TelegramService{
    void sendAlterMessage(AlterMessage alterMessage);

    void sendAlterMessage(MultiMessage multiMessage);

    Message sendMultiMessage(MultiMessage multiMessage);
    void deleteMessage(PurgeMessage purgeMessage);
    void sendMediaMessage(MediaMessage message);
}
