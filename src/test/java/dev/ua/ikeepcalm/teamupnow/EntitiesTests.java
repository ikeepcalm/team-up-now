package dev.ua.ikeepcalm.teamupnow;

import dev.ua.ikeepcalm.teamupnow.database.dao.service.impls.CredentialsService;
import dev.ua.ikeepcalm.teamupnow.database.entities.*;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.AgeENUM;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.GameENUM;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.LanguageENUM;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.ProgressENUM;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

@DataJpaTest
@RunWith(SpringRunner.class)
@ComponentScan(basePackages = "dev.ua.ikeepcalm.teamupnow")
public class EntitiesTests {

    @Autowired
    private CredentialsService credentialsService;

    @Test
    public void testCredentialsLanguage() {
        Credentials credentials = new Credentials();
        credentials.setUsername("test");
        credentials.setAccountId(12345L);
        credentials.setUiLanguage(LanguageENUM.ENGLISH);
        credentialsService.save(credentials);
        Credentials savedCredentials = credentialsService.findByAccountId(12345L);
        Assertions.assertEquals(LanguageENUM.ENGLISH, savedCredentials.getUiLanguage());
    }

    @Test
    public void testCredentialsGame() {
        Credentials credentials = new Credentials();
        credentials.setUsername("test");
        credentials.setAccountId(12345L);
        credentials.setUiLanguage(LanguageENUM.ENGLISH);

        Game game1 = new Game();
        game1.setName(GameENUM.CSGO);
        Game game2 = new Game();
        game2.setName(GameENUM.MINECRAFT);
        credentials.getGames().add(game1);
        credentials.getGames().add(game2);

        credentialsService.save(credentials);
        Credentials savedCredentials = credentialsService.findByAccountId(12345L);
        Assertions.assertEquals(2, savedCredentials.getGames().size());
    }

    @Test
    public void testCredentialsDemographic() {
        Credentials credentials = new Credentials();
        credentials.setUsername("test");
        credentials.setAccountId(12345L);
        credentials.setUiLanguage(LanguageENUM.ENGLISH);
        Demographic demographic = new Demographic();
        demographic.setAge(AgeENUM.ADULT);
        credentials.setDemographic(demographic);
        credentialsService.save(credentials);
        Credentials savedCredentials = credentialsService.findByAccountId(12345L);
        Assertions.assertEquals(AgeENUM.ADULT, savedCredentials.getDemographic().getAge());
    }

    @Test
    public void testCredentialsDescription() {
        Credentials credentials = new Credentials();
        credentials.setUsername("test");
        credentials.setAccountId(12345L);
        credentials.setUiLanguage(LanguageENUM.ENGLISH);
        Description description = new Description();
        description.setDescription("Test description");
        credentials.setDescription(description);
        credentialsService.save(credentials);
        Credentials savedCredentials = credentialsService.findByAccountId(12345L);
        Assertions.assertEquals("Test description", savedCredentials.getDescription().getDescription());
    }

    @Test
    public void testCredentialsProgress() {
        Credentials credentials = new Credentials();
        credentials.setUsername("test");
        credentials.setAccountId(12345L);
        credentials.setUiLanguage(LanguageENUM.ENGLISH);
        Progress progress = new Progress();
        progress.setProgressENUM(ProgressENUM.DONE);
        credentials.setProgress(progress);
        credentialsService.save(credentials);
        Credentials savedCredentials = credentialsService.findByAccountId(12345L);
        Assertions.assertNotEquals(ProgressENUM.START, savedCredentials.getProgress().getProgressENUM());
    }
}
