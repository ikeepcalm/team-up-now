package dev.ua.ikeepcalm.teamupnow;

import dev.ua.ikeepcalm.teamupnow.database.dao.repo.MatchRepo;
import dev.ua.ikeepcalm.teamupnow.database.dao.service.impls.CredentialsService;
import dev.ua.ikeepcalm.teamupnow.database.dao.service.impls.MatchService;
import dev.ua.ikeepcalm.teamupnow.database.entities.Credentials;
import dev.ua.ikeepcalm.teamupnow.database.entities.Demographic;
import dev.ua.ikeepcalm.teamupnow.database.entities.Game;
import dev.ua.ikeepcalm.teamupnow.database.entities.Match;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.AgeENUM;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.GameENUM;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.LanguageENUM;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@DataJpaTest
@RunWith(SpringRunner.class)
@ComponentScan(basePackages = "dev.ua.ikeepcalm.teamupnow")
public class MatchingTests {

    @Autowired
    private CredentialsService credentialsService;
    @Autowired
    private MatchService matchService;

    @Before
    public void createEntities(){
        Game minecraft = new Game(); minecraft.setName(GameENUM.MINECRAFT);
        Game destiny = new Game(); destiny.setName(GameENUM.DESTINY2);
        Game csgo = new Game(); csgo.setName(GameENUM.CSGO);

        Credentials user = new Credentials();
        user.setAccountId(1L);
        user.setUsername("1L");
        user.setUiLanguage(LanguageENUM.ENGLISH);
        user.getGames().add(minecraft);
        user.getGames().add(destiny);
        Demographic demographic = new Demographic();
        demographic.setAge(AgeENUM.YOUND_ADULT);
        user.setDemographic(demographic);
        credentialsService.save(user);

        Credentials user1 = new Credentials();
        user1.setUsername("2L");
        user1.setAccountId(2L);
        user1.setUiLanguage(LanguageENUM.ENGLISH);
        user1.getGames().add(minecraft);
        user1.getGames().add(destiny);
        Demographic demographic1 = new Demographic();
        demographic1.setAge(AgeENUM.YOUND_ADULT);
        user1.setDemographic(demographic1);
        credentialsService.save(user1);

        Credentials user2 = new Credentials();
        user2.setUsername("3L");
        user2.setAccountId(3L);
        user2.setUiLanguage(LanguageENUM.UKRAINIAN);
        user2.getGames().add(minecraft);
        user2.getGames().add(csgo);
        Demographic demographic2 = new Demographic();
        demographic2.setAge(AgeENUM.ADULT);
        user2.setDemographic(demographic2);
        credentialsService.save(user2);

        matchService.createMatchesForUser(user);
    }

    @Test
    public void testMatchesQuantity(){
        List<Match> matches = matchService.findAll();
        Assertions.assertThat(matches).hasSize(2);
    }

    @Test
    public void testMatchesAccountIds(){
        List<Match> matches = matchService.findAll();
        Match match1 = matches.get(0);
        Assertions.assertThat(match1.getFirstUser().getAccountId()).isEqualTo(1L);
        Assertions.assertThat(match1.getFirstUser().getAccountId()).isEqualTo(2L);
    }

    @Test
    public void testMatchesScores(){
        List<Match> matches = matchService.findAll();
        Match match1 = matches.get(0);
        Match match2 = matches.get(1);
        Assertions.assertThat(match1.getScore()).isEqualTo(100);
        Assertions.assertThat(match2.getScore()).isEqualTo(13); //20 for index which doesn't depend on games amount
    }
}
