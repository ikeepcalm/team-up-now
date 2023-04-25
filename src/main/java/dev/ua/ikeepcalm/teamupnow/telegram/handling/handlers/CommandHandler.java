package dev.ua.ikeepcalm.teamupnow.telegram.handling.handlers;

import dev.ua.ikeepcalm.teamupnow.telegram.executing.commands.profile.AboutCommand;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.commands.profile.AgeCommand;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.commands.profile.GamesCommand;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.commands.MenuCommand;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.commands.StartCommand;
import dev.ua.ikeepcalm.teamupnow.telegram.handling.Handleable;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.TelegramService;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.MultiMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class CommandHandler implements Handleable {

    @Autowired
    private TelegramService telegramService;

    @Autowired
    private StartCommand startCommand;
    @Autowired
    private GamesCommand gamesCommand;
    @Autowired
    private AgeCommand ageCommand;
    @Autowired
    private AboutCommand aboutCommand;
    @Autowired
    private MenuCommand menuCommand;

    @Override
    public void manage(Update update) {
        Message origin = update.getMessage();
//        String[] paths = {"menu.jpg", "profile.jpg", "settings.jpg", "info.jpg", "discover.jpg", "explore.jpg", "matches.jpg"};
//        MultiMessage multiMessage = new MultiMessage();
//        multiMessage.setChatId(update.getMessage().getChatId());
//        multiMessage.setText("1");
//        for (String s : paths){
//            multiMessage.setFilePath("src/main/resources/img/" + s);
//            Message message = telegramService.sendMultiMessage(multiMessage);
//            System.out.println(s + " " + message.getPhoto().toString());
//        }
        switch (origin.getText()) {
            case "/start" -> startCommand.execute(origin);
            case "/games" -> gamesCommand.execute(origin);
            case "/age" -> ageCommand.execute(origin);
            case "/about" -> aboutCommand.execute(origin);
            case "/menu" -> menuCommand.execute(origin);
        }
    }

    @Override
    public boolean supports(Update update) {
        if (update.getMessage() != null){
            return update.getMessage().getText().startsWith("/");
        } else {
            return false;
        }
    }
}
