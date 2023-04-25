package dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies;

import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

@Getter
@Setter
public class MultiMessage {
    private String text;
    private long chatId;
    private int messageId;
    private String filePath;
    private ReplyKeyboard replyKeyboard;
}
