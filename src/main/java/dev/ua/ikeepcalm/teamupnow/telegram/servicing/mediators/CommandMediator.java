package dev.ua.ikeepcalm.teamupnow.telegram.servicing.mediators;

import dev.ua.ikeepcalm.teamupnow.telegram.executing.commands.profile.AboutCommand;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.commands.profile.AgeCommand;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.commands.profile.GamesCommand;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.commands.system.MenuCommand;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.commands.system.NotifyCommand;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.commands.system.StartCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class CommandMediator {
    private final AboutCommand aboutCommand;
    private final AgeCommand ageCommand;
    private final GamesCommand gamesCommand;
    private final MenuCommand menuCommand;
    private final StartCommand startCommand;
    private final NotifyCommand notifyCommand;

    public void executeStartCommand(Message origin){
        startCommand.execute(origin);
    }

    public void executeMenuCommand(Message origin){
        menuCommand.execute(origin);
    }

    public void executeGamesCommand(Message origin){
        gamesCommand.execute(origin);
    }

    public void executeAgeCommand(Message origin){
        ageCommand.execute(origin);
    }

    public void executeAboutCommand(Message origin){
        aboutCommand.execute(origin);
    }

    public void executeNotifyCommand(Message origin){
        notifyCommand.execute(origin);
    }

    @Autowired
    public CommandMediator(AboutCommand aboutCommand,
                           AgeCommand ageCommand,
                           GamesCommand gamesCommand,
                           MenuCommand menuCommand,
                           StartCommand startCommand, NotifyCommand notifyCommand) {
        this.aboutCommand = aboutCommand;
        this.ageCommand = ageCommand;
        this.gamesCommand = gamesCommand;
        this.menuCommand = menuCommand;
        this.startCommand = startCommand;
        this.notifyCommand = notifyCommand;
    }
}
