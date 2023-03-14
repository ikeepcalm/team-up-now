package dev.ua.ikeepcalm.teamupnow.telegram.models;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ToDelete {

    @NotNull
    private int messageId;
    @NotNull
    private long chatId;

    public ToDelete(int messageId, long chatId) {
        this.messageId = messageId;
        this.chatId = chatId;
    }
}
