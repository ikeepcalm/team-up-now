package dev.ua.ikeepcalm.teamupnow.database.dao.service.impls;

import dev.ua.ikeepcalm.teamupnow.database.dao.repo.MatchRepo;
import dev.ua.ikeepcalm.teamupnow.database.entities.Credentials;
import dev.ua.ikeepcalm.teamupnow.database.entities.Game;
import dev.ua.ikeepcalm.teamupnow.database.entities.Match;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.GameENUM;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.LanguageENUM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MatchService {

    @Autowired
    private MatchRepo matchRepo;
    @Autowired
    private CredentialsService credentialsService;

    public void createMatchesForUser(Credentials user) {
        List<Credentials> credentialsList = credentialsService.findAllExcept(user.getAccountId());

        List<Match> matches = new ArrayList<>();

        for (Credentials foundUser : credentialsList) {
            Match match = matchRepo.findByFirstUserIdAndSecondUserId(user.getAccountId(), foundUser.getAccountId())
                    .orElse(new Match());

            match.setFirstUser(user);
            match.setSecondUser(foundUser);

            int score = 0;

            switch (user.getUiLanguage()) {
                case ENGLISH:
                    if (user.getUiLanguage() == foundUser.getUiLanguage()) {
                        score += 40;
                    }
                    break;
                case UKRAINIAN:
                    if (foundUser.getUiLanguage() == LanguageENUM.UKRAINIAN) {
                        score += 30;
                    }
                    break;
            }

            Set<GameENUM> gameEnums1 = user.getGames().stream().map(Game::getName).collect(Collectors.toSet());
            Set<GameENUM> gameEnums2 = foundUser.getGames().stream().map(Game::getName).collect(Collectors.toSet());
            Set<GameENUM> intersection = new HashSet<>(gameEnums1);
            intersection.retainAll(gameEnums2);
            double jaccardIndex = (double) intersection.size() / (gameEnums1.size() + gameEnums2.size() - intersection.size());
            int similarityScore = (int) Math.round(jaccardIndex * 40);
            score += similarityScore;

            if (user.getDemographic().getAge() == foundUser.getDemographic().getAge()) {
                score += 20;
            }

            match.setScore(score);
            match.setFirstUserLiked(false);
            match.setSecondUserLiked(false);

            matches.add(match);
        }
        matchRepo.saveAll(matches);
    }


    public List<Match> findMatchesForUser(Credentials user) {
        List<Match> foundMatches = matchRepo.findMatchesByFirstUser(user);
        List<Match> matchesToReturn = new ArrayList<>();
        for (Match match : foundMatches){
            if (match.isFirstUserLiked()){
                matchesToReturn.add(match);
            }
        } return matchesToReturn;
    }

    public List<Match> findAllMatchesForUser(Credentials user){
        return (List<Match>) matchRepo.findMatchesByFirstUser(user);
    }

    public List<Match> findAll(){
        return (List<Match>) matchRepo.findAll();
    }
}
