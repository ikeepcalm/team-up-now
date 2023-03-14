package dev.ua.ikeepcalm.teamupnow.telegram.models;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

@Getter
@Setter
public class Callback {
    @NotNull
    private String text;
    @NotNull
    private long chatId;

    @NotNull
    private ReplyKeyboard replyKeyboard;

    public Callback(String text, long chatId, ReplyKeyboard replyKeyboard) {
        this.text = text;
        this.chatId = chatId;
        this.replyKeyboard = replyKeyboard;
    }

    public Callback() {
    }
}
