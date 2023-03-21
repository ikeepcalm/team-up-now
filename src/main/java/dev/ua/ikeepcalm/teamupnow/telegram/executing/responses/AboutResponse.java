package dev.ua.ikeepcalm.teamupnow.telegram.executing.responses;

import dev.ua.ikeepcalm.teamupnow.aop.annotations.Progressable;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.ProgressENUM;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.services.TelegramService;
import dev.ua.ikeepcalm.teamupnow.telegram.proxies.MultiMessage;
import dev.ua.ikeepcalm.teamupnow.telegram.proxies.PurgeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;

@Component
public class AboutResponse {
    @Autowired
    private TelegramService telegramService;

    @Progressable(ProgressENUM.ABOUT)
    public void execute(Update update) {
        {
            MultiMessage multiMessage = new MultiMessage();
            multiMessage.setText("Easter-egg fact: you can actually buy a flying bicycle");
            multiMessage.setChatId(update.getMessage().getChatId());
            ReplyKeyboardRemove remove = new ReplyKeyboardRemove();
            remove.setRemoveKeyboard(true);
            multiMessage.setReplyKeyboard(remove);
            org.telegram.telegrambots.meta.api.objects.Message message =  telegramService.sendMultiMessage(multiMessage);
            PurgeMessage purgeMessage = new PurgeMessage(message.getMessageId(), message.getChatId());
            telegramService.deleteMessage(purgeMessage);
        }
        MultiMessage message = new MultiMessage();
        message.setText("Now, send a message with a small description of yourself that you want other users to see." +
                " For example: \"Looking for friendly CS:GO players, just to play in the evenings in cosy company <3\".");
        message.setChatId(update.getMessage().getChatId());
        telegramService.sendMultiMessage(message);
    }
}
