package dev.ua.ikeepcalm.teamupnow.database.dao.service.impls;

import dev.ua.ikeepcalm.teamupnow.database.dao.repo.MatchRepo;
import dev.ua.ikeepcalm.teamupnow.database.entities.Credentials;
import dev.ua.ikeepcalm.teamupnow.database.entities.Game;
import dev.ua.ikeepcalm.teamupnow.database.entities.Match;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.GameENUM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MatchService {

    @Autowired
    private MatchRepo matchRepo;
    @Autowired
    private CredentialsService credentialsService;

    public void createMatchesForUser(Credentials user) {
        List<Credentials> credentialsList = credentialsService.findAllExcept(user.getAccountId());
        for (Credentials foundUser : credentialsList) {
            Match match = new Match();
            match.setFirstUserId(user.getAccountId());
            match.setSecondUserId(foundUser.getAccountId());
            {
                if (user.getUiLanguage() == foundUser.getUiLanguage()) {
                    match.setScore(40);
                } else {
                    match.setScore(0);
                }
            }//Language
            {
                Set<GameENUM> gameEnums1 = user.getGames().stream().map(Game::getName).collect(Collectors.toSet());
                Set<GameENUM> gameEnums2 = foundUser.getGames().stream().map(Game::getName).collect(Collectors.toSet());
                Set<GameENUM> intersection = new HashSet<>(gameEnums1);
                intersection.retainAll(gameEnums2);
//                Doesn't depend on games amount, just on the similarity
//                double jaccardIndex = (double) intersection.size() / (Math.max(gameEnums1.size(), gameEnums2.size()));
                double jaccardIndex = (double) intersection.size() / (gameEnums1.size() + gameEnums2.size() - intersection.size());
                int similarityScore = (int) Math.round(jaccardIndex * 40);
                match.setScore((match.getScore() + similarityScore));
            }//Games
            {
                if (user.getDemographic().getAge() == foundUser.getDemographic().getAge()) {
                    match.setScore(match.getScore() + 20);
                }
            }//Age
            {
                match.setFirstUserLiked(false);
                match.setSecondUserLiked(false);
            }//Liked default
            matchRepo.save(match);
        }
    }

    public List<Match> findMatchesForUser(Credentials user) {
        List<Match> foundMatches = matchRepo.findByFirstUserAndDescScore(user.getAccountId());
        List<Match> matchesToReturn = new ArrayList<>();
        for (Match match : foundMatches){
            if (match.isFirstUserLiked()){
                matchesToReturn.add(match);
            }
        } return matchesToReturn;
    }

    public List<Match> findAllMatchesForUser(Credentials user){
        return matchRepo.findByFirstUserAndDescScore(user.getAccountId());
    }

    public List<Match> findAll(){
        return (List<Match>) matchRepo.findAll();
    }
}
