//package dev.ua.ikeepcalm.teamupnow.telegram.handling.subhandlers;
//
//import dev.ua.ikeepcalm.teamupnow.telegram.commands.ProfileCommand;
//import dev.ua.ikeepcalm.teamupnow.telegram.models.EditMessage;
//import dev.ua.ikeepcalm.teamupnow.telegram.models.ToDelete;
//import dev.ua.ikeepcalm.teamupnow.telegram.services.TelegramService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.telegram.telegrambots.meta.api.objects.Message;
//import org.telegram.telegrambots.meta.api.objects.Update;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
//
//import java.util.List;
//
//@Component
//public class GamesSubHandler implements SubHandler {
//
//    private final TelegramService telegramService;
//    private final ProfileCommand profileCommand;
//
//    @Autowired
//    public GamesSubHandler(TelegramService telegramService, ProfileCommand profileCommand) {
//        this.telegramService = telegramService;
//        this.profileCommand = profileCommand;
//    }
//
//    @Override
//    public void manage(Update update) {
//        String currentCallback = update.getCallbackQuery().getData();
//        Long chatId = update.getCallbackQuery().getMessage().getChatId();
//        Message origin = update.getCallbackQuery().getMessage();
//        InlineKeyboardMarkup markup = origin.getReplyMarkup();
//        if (!currentCallback.equals("profile-games-ready")) {
//            try {
//                if (currentCallback.startsWith("profile-games-minecraft")) {
//                    updateButton("profile-games-minecraft", markup, "Minecraft");
//                } else if (currentCallback.startsWith("profile-games-csgo")) {
//                    updateButton("profile-games-csgo", markup, "CS:GO");
//                } else if (currentCallback.startsWith("profile-games-destiny2")) {
//                    updateButton("profile-games-destiny2", markup, "Destiny 2");
//                }
//            } finally {
//                EditMessage editMessage = new EditMessage();
//                editMessage.setMessageId(origin.getMessageId());
//                editMessage.setChatId(origin.getChatId());
//                editMessage.setReplyKeyboard(markup);
//                telegramService.sendEditMessage(editMessage);
//            }
//        } else {
//            //TODO: Save games
//            telegramService.deleteCallback(new ToDelete(update.getCallbackQuery().getMessage().getMessageId(), chatId));
//            profileCommand.executeAge(chatId);
//        }
//    }
//
//    private void updateButton(String callbackData, InlineKeyboardMarkup markup, String buttonText) {
//        for (List<InlineKeyboardButton> row : markup.getKeyboard()) {
//            for (InlineKeyboardButton button : row) {
//                if (button.getCallbackData().startsWith(callbackData)) {
//                    if (button.getCallbackData().endsWith("-chosen")) {
//                        button.setText(buttonText);
//                        button.setCallbackData(callbackData);
//                    } else {
//                        button.setText(buttonText + " ✓");
//                        button.setCallbackData(callbackData + "-chosen");
//                    }
//                }
//            }
//        }
//    }
//}
