package dev.ua.ikeepcalm.teamupnow;

import dev.ua.ikeepcalm.teamupnow.database.dao.repo.CredentialsRepo;
import dev.ua.ikeepcalm.teamupnow.database.dao.service.impls.CredentialsService;
import dev.ua.ikeepcalm.teamupnow.telegram.BotInstance;
import dev.ua.ikeepcalm.teamupnow.telegram.TelegramConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@org.springframework.context.annotation.Configuration
@Import({TelegramConfiguration.class})
public class Configuration {

    @Bean
    public BotInstance botInstance(){
        return new BotInstance();
    }

}
