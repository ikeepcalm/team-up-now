package dev.ua.ikeepcalm.teamupnow.telegram.proxies;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

@Getter
@Setter
public class MultiMessage {
    @NotNull
    private String text;
    @NotNull
    private long chatId;

    private ReplyKeyboard replyKeyboard;


    public MultiMessage(String text, long chatId, ReplyKeyboard replyKeyboard) {
        this.text = text;
        this.chatId = chatId;
        this.replyKeyboard = replyKeyboard;
    }

    public MultiMessage() {
    }
}
