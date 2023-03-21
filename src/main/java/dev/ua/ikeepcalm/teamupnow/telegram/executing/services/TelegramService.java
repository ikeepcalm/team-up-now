package dev.ua.ikeepcalm.teamupnow.telegram.executing.services;


import dev.ua.ikeepcalm.teamupnow.telegram.proxies.MediaMessage;
import dev.ua.ikeepcalm.teamupnow.telegram.proxies.MultiMessage;
import dev.ua.ikeepcalm.teamupnow.telegram.proxies.AlterMessage;
import dev.ua.ikeepcalm.teamupnow.telegram.proxies.PurgeMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface TelegramService{
    void sendEditMessage(AlterMessage alterMessage);
    Message sendMultiMessage(MultiMessage multiMessage);
    void deleteMessage(PurgeMessage purgeMessage);
    void sendMediaMessage(MediaMessage message);
}
