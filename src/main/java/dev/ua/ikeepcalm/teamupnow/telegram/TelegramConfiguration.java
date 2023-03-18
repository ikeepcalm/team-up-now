package dev.ua.ikeepcalm.teamupnow.telegram;

import dev.ua.ikeepcalm.teamupnow.aop.aspects.ProgressionAspect;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.responses.AboutResponse;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.responses.AgeResponse;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.responses.GamesResponse;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.responses.StartResponse;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.services.TelegramService;
import dev.ua.ikeepcalm.teamupnow.telegram.executing.services.implementations.TelegramServiceExecutor;
import dev.ua.ikeepcalm.teamupnow.telegram.handling.handlers.implementation.CallbackHandler;
import dev.ua.ikeepcalm.teamupnow.telegram.handling.handlers.implementation.CommandHandler;
import dev.ua.ikeepcalm.teamupnow.telegram.handling.handlers.Handler;
import jakarta.inject.Singleton;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableAspectJAutoProxy
public class TelegramConfiguration {

    @Bean
    @Singleton
    public List<Handler> handlerList(){
        List<Handler> handlerList = new ArrayList<>();
        handlerList.add(new CommandHandler());
        handlerList.add(new CallbackHandler());
        return handlerList;
    }

    @Bean
    public StartResponse startResponse(){
        return new StartResponse();
    }

    @Bean
    public GamesResponse gamesResponse(){
        return new GamesResponse();
    }

    @Bean
    public AgeResponse ageResponse(){
        return new AgeResponse();
    }

    @Bean
    public AboutResponse aboutResponse(){
        return new AboutResponse();
    }

    @Bean
    public ProgressionAspect progressionAspect(){
        return new ProgressionAspect();
    }

    @Bean
    public TelegramService telegramService(){
        return new TelegramServiceExecutor();
    }

}
