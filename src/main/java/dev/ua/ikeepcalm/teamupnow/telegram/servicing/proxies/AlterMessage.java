package dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Getter
@Setter
public class AlterMessage {

    @NotNull
    private int messageId;
    @NotNull
    private Long chatId;
    private String text;
    private InlineKeyboardMarkup replyKeyboard;
    private String fileURL;
    public AlterMessage() {
    }
}
