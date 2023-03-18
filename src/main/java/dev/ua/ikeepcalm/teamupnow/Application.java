package dev.ua.ikeepcalm.teamupnow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = "dev.ua.ikeepcalm.teamupnow")
@ComponentScan(basePackages = {
        "dev.ua.ikeepcalm.teamupnow.telegram",
        "dev.ua.ikeepcalm.teamupnow.database",
        "dev.ua.ikeepcalm.teamupnow.aop",
        "org.telegram.telegrambots"
})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}