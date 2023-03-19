//package dev.ua.ikeepcalm.teamupnow.telegram.handling.subhandlers;
//
//
//import dev.ua.ikeepcalm.teamupnow.telegram.executing.services.TelegramService;
//import dev.ua.ikeepcalm.teamupnow.telegram.proxies.ToDelete;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.telegram.telegrambots.meta.api.objects.Update;
//
//@Component
//public class AgeSubHandler implements SubHandler {
//
//    @Autowired
//    private TelegramService telegramService;
//
//    @Override
//    public void manage(Update update) {
//        String currentCallback = update.getCallbackQuery().getData();
//        Long chatId = update.getCallbackQuery().getMessage().getChatId();
//        int messageId = update.getCallbackQuery().getMessage().getMessageId();
//        try {
//            if (currentCallback.equals("profile-age-category-young")) {
//                //TODO: Save age category
//            } else if (currentCallback.equals("profile-age-category-young-adult")) {
//                //TODO: Save age category
//            } else if (currentCallback.equals("profile-age-category-adult")){
//                //TODO: Save age category
//            }
//        } finally {
//            profileCommand.executeAbout(chatId);
//            telegramService.deleteCallback(new ToDelete(messageId,chatId));
//            //TODO: Users' entities in database, especially value of their current step of filling up the init info
//            // to eliminate possible vulnerabilities and to continue developing bot
//        }
//    }
//}
