package dev.ua.ikeepcalm.teamupnow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"dev.ua.ikeepcalm.teamupnow", "org.telegram.telegrambots"})
public class Application {

    //TODO: Make all uploaded photos the same width and length
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}