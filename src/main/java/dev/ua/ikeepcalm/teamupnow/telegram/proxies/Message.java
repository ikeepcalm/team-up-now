package dev.ua.ikeepcalm.teamupnow.telegram.proxies;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message {

    @NotNull
    private String text;
    @NotNull
    private long chatId;

    public Message(String text, long chatId) {
        this.text = text;
        this.chatId = chatId;
    }

    public Message() {
    }
}
