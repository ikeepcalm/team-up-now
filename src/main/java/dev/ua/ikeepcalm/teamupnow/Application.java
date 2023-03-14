package dev.ua.ikeepcalm.teamupnow;

import dev.ua.ikeepcalm.teamupnow.telegram.core.BotInstance;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
public class Application {

    public static final ApplicationContext context = new AnnotationConfigApplicationContext(Configuration.class);
    public static void main(String[] args) {
        try {
            new TelegramBotsApi(DefaultBotSession.class).registerBot(new BotInstance());
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
        SpringApplication.run(Application.class, args);
    }
}
