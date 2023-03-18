package dev.ua.ikeepcalm.teamupnow;

import dev.ua.ikeepcalm.teamupnow.telegram.BotInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication(scanBasePackages = "dev.ua.ikeepcalm.teamupnow")
public class Application {

    public static final ApplicationContext ctx = new AnnotationConfigApplicationContext(Configuration.class);

    public static void main(String[] args) {
        try {
            SpringApplication.run(Application.class, args);
            new TelegramBotsApi(DefaultBotSession.class).registerBot(ctx.getBean(BotInstance.class));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}