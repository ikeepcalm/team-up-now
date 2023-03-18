package dev.ua.ikeepcalm.teamupnow.telegram.proxies;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

@Getter
@Setter
public class EditMessage {

    @NotNull
    private int messageId;

    @NotNull
    private Long chatId;

    private InlineKeyboardMarkup replyKeyboard;

    public EditMessage(int messageId, Long chatId, InlineKeyboardMarkup replyKeyboard) {
        this.messageId = messageId;
        this.chatId = chatId;
        this.replyKeyboard = replyKeyboard;
    }

    public EditMessage(int messageId, Long chatId) {
        this.messageId = messageId;
        this.chatId = chatId;
    }

    public EditMessage() {
    }
}
