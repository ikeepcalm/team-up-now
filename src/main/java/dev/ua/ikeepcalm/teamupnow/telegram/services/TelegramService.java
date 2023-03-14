package dev.ua.ikeepcalm.teamupnow.telegram.services;

import dev.ua.ikeepcalm.teamupnow.telegram.models.Callback;
import dev.ua.ikeepcalm.teamupnow.telegram.models.EditMessage;
import dev.ua.ikeepcalm.teamupnow.telegram.models.Message;
import dev.ua.ikeepcalm.teamupnow.telegram.models.ToDelete;

public interface TelegramService{
    void sendMessage(Message message);
    void sendEditMessage(EditMessage editMessage);
    void sendCallback(Callback callback);

    void deleteCallback(ToDelete toDelete);
}
