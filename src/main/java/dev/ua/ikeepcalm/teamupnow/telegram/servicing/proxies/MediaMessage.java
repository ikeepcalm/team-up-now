package dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

@Getter
@Setter
public class MediaMessage {
    private String text;
    @NotNull
    private Long chatId;
    private ReplyKeyboard replyKeyboard;
    private String filePath;
    public MediaMessage() {
    }
}
