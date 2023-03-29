package dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

@Getter
@Setter
public class MultiMessage {
    @NotNull
    private String text;
    @NotNull
    private long chatId;
    private int messageId;
    private ReplyKeyboard replyKeyboard;
    public MultiMessage() {
    }
}
