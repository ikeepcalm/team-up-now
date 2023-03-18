//package dev.ua.ikeepcalm.teamupnow.telegram.handling.subhandlers;
//
//import dev.ua.ikeepcalm.teamupnow.telegram.commands.ProfileCommand;
//import dev.ua.ikeepcalm.teamupnow.telegram.models.ToDelete;
//import dev.ua.ikeepcalm.teamupnow.telegram.services.TelegramService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.telegram.telegrambots.meta.api.objects.Update;
//
//@Component
//public class LanguageSubHandler implements SubHandler {
//
//    private final TelegramService telegramService;
//    private final ProfileCommand profileCommand;
//
//    @Autowired
//    public LanguageSubHandler(TelegramService telegramService, ProfileCommand profileCommand) {
//        this.telegramService = telegramService;
//        this.profileCommand = profileCommand;
//    }
//
//    @Override
//    public void manage(Update update) {
//        String currentCallback = update.getCallbackQuery().getData();
//        Long chatId = update.getCallbackQuery().getMessage().getChatId();
//        int messageId = update.getCallbackQuery().getMessage().getMessageId();
//        try {
//            if (currentCallback.equals("profile-language-english")) {
//                //TODO: Save main language and use it afterwards
//            } else if (currentCallback.equals("profile-language-ukrainian")) {
//                //TODO: Save main language and use it afterwards
//            }
//        } finally {
//            profileCommand.executeGames(chatId);
//            telegramService.deleteCallback(new ToDelete(messageId,chatId));
//        }
//    }
//}
//
