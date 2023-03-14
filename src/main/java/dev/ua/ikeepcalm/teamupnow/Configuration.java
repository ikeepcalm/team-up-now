package dev.ua.ikeepcalm.teamupnow;

import dev.ua.ikeepcalm.teamupnow.telegram.core.AssignNode;
import dev.ua.ikeepcalm.teamupnow.telegram.handlers.impls.CallbackHandler;
import dev.ua.ikeepcalm.teamupnow.telegram.handlers.impls.CommandHandler;
import dev.ua.ikeepcalm.teamupnow.telegram.handlers.impls.MediaHandler;
import dev.ua.ikeepcalm.teamupnow.telegram.handlers.subhandlers.impls.AgeSubHandler;
import dev.ua.ikeepcalm.teamupnow.telegram.handlers.subhandlers.impls.GamesSubHandler;
import dev.ua.ikeepcalm.teamupnow.telegram.handlers.subhandlers.impls.LanguageSubHandler;
import dev.ua.ikeepcalm.teamupnow.telegram.services.CallbackService;
import dev.ua.ikeepcalm.teamupnow.telegram.services.TelegramService;
import dev.ua.ikeepcalm.teamupnow.telegram.services.impls.CallbackServiceExecutor;
import dev.ua.ikeepcalm.teamupnow.telegram.services.impls.TelegramServiceExecutor;
import jakarta.inject.Singleton;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@org.springframework.context.annotation.Configuration
@ComponentScan(basePackages = "dev.ua.ikeepcalm.teamupnow.telegram")
public class Configuration {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return new DriverManagerDataSource();
    }

    @Bean
    @Singleton
    public TelegramService telegramService() {
        return new TelegramServiceExecutor();
    }

    @Bean
    @Singleton
    public CallbackService callbackService() {
        return new CallbackServiceExecutor();
    }

    @Bean
    @Singleton
    public CommandHandler commandHandler() {
        return new CommandHandler(telegramService());
    }

    @Bean
    @Singleton
    public CallbackHandler callbackHandler() {
        return new CallbackHandler(telegramService(), gamesSubHandler(), ageSubHandler(), languageSubHandler());
    }

    @Bean
    @Singleton
    public MediaHandler mediaHandler() {
        return new MediaHandler(telegramService());
    }

    @Bean
    @Singleton
    public GamesSubHandler gamesSubHandler(){
        return new GamesSubHandler();
    }

    @Bean
    @Singleton
    public AgeSubHandler ageSubHandler(){
        return new AgeSubHandler();
    }

    @Bean
    @Singleton
    public LanguageSubHandler languageSubHandler(){
        return new LanguageSubHandler();
    }

    @Bean
    @Singleton
    public AssignNode assignNode() {
        return new AssignNode(commandHandler(), callbackHandler(), mediaHandler());
    }
}
