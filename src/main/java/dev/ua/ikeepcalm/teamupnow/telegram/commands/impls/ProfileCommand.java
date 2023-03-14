package dev.ua.ikeepcalm.teamupnow.telegram.commands.impls;

import dev.ua.ikeepcalm.teamupnow.telegram.commands.Command;
import dev.ua.ikeepcalm.teamupnow.telegram.models.Callback;
import dev.ua.ikeepcalm.teamupnow.telegram.models.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class ProfileCommand implements Command {

    //TODO: Weave this code into user settings after initialization
    public void executeLanguage(Long chatId) {
        {
            Callback callback = new Callback();
            callback.setText("Choose the language in which you want me to communicate with you further:");
            callback.setChatId(chatId);
            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
            List<InlineKeyboardButton> firstRow = new ArrayList<>();
            InlineKeyboardButton english = new InlineKeyboardButton();
            english.setText("English \uD83C\uDDEC\uD83C\uDDE7");
            english.setCallbackData("profile-language-english");
            InlineKeyboardButton ukrainian = new InlineKeyboardButton();
            ukrainian.setText("Ukrainian \uD83C\uDDFA\uD83C\uDDE6");
            ukrainian.setCallbackData("profile-language-ukrainian");
            firstRow.add(english);
            firstRow.add(ukrainian);
            keyboard.add(firstRow);
            inlineKeyboardMarkup.setKeyboard(keyboard);
            callback.setReplyKeyboard(inlineKeyboardMarkup);
            telegramService.sendCallback(callback);
        }
    }

    public void executeGames(Long chatId) {
        {
            Callback callback = new Callback();
            callback.setText("This is how you will interact with me in most cases. And now for the actual encounter:");
            callback.setChatId(chatId);
            ReplyKeyboardRemove remove = new ReplyKeyboardRemove();
            remove.setRemoveKeyboard(true);
            callback.setReplyKeyboard(remove);
            telegramService.sendCallback(callback);
        }
        Callback callback = new Callback();
        callback.setText("Select the games you play (you can choose more than one)." +
                " When you're done, click the green check mark to continue.");
        callback.setChatId(chatId);
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        List<InlineKeyboardButton> secondRow = new ArrayList<>();
        InlineKeyboardButton minecraft = new InlineKeyboardButton();
        minecraft.setText("Minecraft");
        minecraft.setCallbackData("profile-games-minecraft");
        InlineKeyboardButton csgo = new InlineKeyboardButton();
        csgo.setText("CS:GO");
        csgo.setCallbackData("profile-games-csgo");
        InlineKeyboardButton destiny2 = new InlineKeyboardButton("Destiny 2");
        destiny2.setText("Destiny 2");
        destiny2.setCallbackData("profile-games-destiny2");
        InlineKeyboardButton ready = new InlineKeyboardButton();
        ready.setText("Ready ✅");
        ready.setCallbackData("profile-games-ready");
        firstRow.add(minecraft);
        firstRow.add(csgo);
        firstRow.add(destiny2);
        secondRow.add(ready);
        keyboard.add(firstRow);
        keyboard.add(secondRow);
        inlineKeyboardMarkup.setKeyboard(keyboard);
        callback.setReplyKeyboard(inlineKeyboardMarkup);
        telegramService.sendCallback(callback);
    }

    public void executeAge(Long chatId) {
        Callback callback = new Callback();
        callback.setText("Now, please, choose your age category (the system will try to match you with allies of similar age):");
        callback.setChatId(chatId);
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        InlineKeyboardButton youngCategory = new InlineKeyboardButton();
        youngCategory.setText("14-18");
        youngCategory.setCallbackData("profile-age-category-young");
        InlineKeyboardButton youndAdultCategory = new InlineKeyboardButton();
        youndAdultCategory.setText("18-26");
        youndAdultCategory.setCallbackData("profile-age-category-young-adult");
        InlineKeyboardButton adultCategory = new InlineKeyboardButton();
        adultCategory.setText("27-35");
        adultCategory.setCallbackData("profile-age-category-adult");
        firstRow.add(youngCategory);
        firstRow.add(youndAdultCategory);
        firstRow.add(adultCategory);
        keyboard.add(firstRow);
        inlineKeyboardMarkup.setKeyboard(keyboard);
        callback.setReplyKeyboard(inlineKeyboardMarkup);
        telegramService.sendCallback(callback);
    }

    public void executeAbout(Long chatId) {
        Message message = new Message();
        message.setText("Now, send a message with a small description of yourself that you want other users to see." +
                " For example: \"Looking for friendly CS:GO players, just to play in the evenings in cosy company <3\".");
        message.setChatId(chatId);
        telegramService.sendMessage(message);
    }
}
