package dev.ua.ikeepcalm.teamupnow.telegram.executing.services;


import dev.ua.ikeepcalm.teamupnow.telegram.proxies.Callback;
import dev.ua.ikeepcalm.teamupnow.telegram.proxies.EditMessage;
import dev.ua.ikeepcalm.teamupnow.telegram.proxies.Message;
import dev.ua.ikeepcalm.teamupnow.telegram.proxies.ToDelete;

public interface TelegramService{
    void sendMessage(Message message);
    void sendEditMessage(EditMessage editMessage);
    void sendCallback(Callback callback);
    void deleteCallback(ToDelete toDelete);
}
