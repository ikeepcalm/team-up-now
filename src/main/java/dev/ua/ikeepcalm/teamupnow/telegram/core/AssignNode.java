package dev.ua.ikeepcalm.teamupnow.telegram.core;

import dev.ua.ikeepcalm.teamupnow.telegram.handlers.impls.CallbackHandler;
import dev.ua.ikeepcalm.teamupnow.telegram.handlers.impls.CommandHandler;
import dev.ua.ikeepcalm.teamupnow.telegram.handlers.impls.MediaHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class AssignNode {

    private final CommandHandler commandHandler;
    private final CallbackHandler callbackHandler;
    private final MediaHandler mediaHandler;

    @Autowired
    public AssignNode(CommandHandler commandHandler, CallbackHandler callbackHandler, MediaHandler mediaHandler) {
        this.callbackHandler = callbackHandler;
        this.commandHandler = commandHandler;
        this.mediaHandler = mediaHandler;
    }

    public boolean supports(Update update) {
        return update.getUpdateId() != null;
    }

    private UpdateType getUpdateType(Update update) {
        if (update.hasCallbackQuery()) {
            return UpdateType.CALLBACK;
        } else if (update.getMessage().hasPhoto() || update.getMessage().hasVideo() || update.getMessage().hasDocument()) {
            return UpdateType.MEDIA;
        } else if (update.getMessage().getText().charAt(0) == '/') {
            return UpdateType.COMMAND;
        } else {
            return UpdateType.UNSUPPORTED;
        }
    }

    public void manage(Update update) {
        switch (getUpdateType(update)) {
            case COMMAND:
                if (commandHandler.supports(update)){
                    commandHandler.manage(update);
                } break;
            case CALLBACK:
                if (callbackHandler.supports(update)){
                    callbackHandler.manage(update);
                } break;
            case MEDIA:
                if (mediaHandler.supports(update)){
                    mediaHandler.manage(update);
                }
                break;
            default:
                // handle unsupported message
                break;
        }
    }
}

enum UpdateType {
    COMMAND,
    CALLBACK,
    MEDIA,
    UNSUPPORTED
}
