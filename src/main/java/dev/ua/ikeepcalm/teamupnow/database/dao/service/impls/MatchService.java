package dev.ua.ikeepcalm.teamupnow.database.dao.service.impls;

import dev.ua.ikeepcalm.teamupnow.database.dao.repo.MatchRepo;
import dev.ua.ikeepcalm.teamupnow.database.entities.Credentials;
import dev.ua.ikeepcalm.teamupnow.database.entities.Game;
import dev.ua.ikeepcalm.teamupnow.database.entities.Match;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.GameENUM;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.LanguageENUM;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.ProgressENUM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MatchService {

    private final MatchRepo matchRepo;
    private final CredentialsService credentialsService;

    @Autowired
    public MatchService(MatchRepo matchRepo, CredentialsService credentialsService) {
        this.matchRepo = matchRepo;
        this.credentialsService = credentialsService;
    }

    public void createMatchesForUser(Credentials user) {
        List<Credentials> retreivedCredentials = credentialsService.findAllExcept(user.getAccountId());
        List<Credentials> credentialsToHandle = new ArrayList<>();
        for (Credentials credentials : retreivedCredentials) {
            if (credentials.getProgress().getProgressENUM() == ProgressENUM.DONE) {
                credentialsToHandle.add(credentials);
            }
        }


        List<Match> matchesToSave = new ArrayList<>();

        for (Credentials retrieved : credentialsToHandle) {
            if (retrieved.equals(user)) {
                continue;
            }

            Match inverseMatch = matchRepo.findByFirstUserAndSecondUser(retrieved, user);
            Match match = matchRepo.findByFirstUserAndSecondUser(user, retrieved);

            if (match == null && inverseMatch == null) {
                match = new Match();
                match.setFirstUser(user);
                match.setSecondUser(retrieved);
                match.setFirstUserLiked(false);
                match.setSecondUserLiked(false);
            } else {
                continue;
            }
            int score = calculateScore(user, retrieved);
            match.setScore(score);
            matchesToSave.add(match);
        }
        matchRepo.saveAll(matchesToSave);
    }

    public void deleteAllMatchesForUser(Credentials user){
        matchRepo.deleteAllByFirstUser(user);
        matchRepo.deleteAllBySecondUser(user);
    }

    public List<Match> findAllMatchesForUser(Credentials user) {
        Comparator<Match> scoreComparator = (o2, o1) -> Integer.compare(o1.getScore(), o2.getScore());

        List<Match> matches = matchRepo.findAllMatchesByUser(user);
        List<Match> matchesToReturn = new ArrayList<>();
        for (Match match : matches) {
            if (match.getFirstUser() == user) {
                if (match.isFirstUserLiked()) {
                } else {
                    matchesToReturn.add(match);
                }
            } else if (match.getSecondUser() == user) {
                if (match.isSecondUserLiked()) {
                } else {
                    matchesToReturn.add(match);
                }
            }
        }
        matchesToReturn.sort(scoreComparator);
        return matchesToReturn;
    }

    public List<Match> findConnectedMatchesForUser(Credentials user) {
        List<Match> matches = matchRepo.findAllMatchesByUser(user);
        List<Match> matchesToReturn = new ArrayList<>();
        for (Match match : matches) {
            if (match.isSecondUserLiked() && match.isFirstUserLiked()) {
                matchesToReturn.add(match);
            }
        }
        return matchesToReturn;
    }


    public List<Match> findAll() {
        return (List<Match>) matchRepo.findAll();
    }

    public void save(Match match) {
        matchRepo.save(match);
    }

    private int calculateScore(Credentials user, Credentials foundUser) {
        int score = 0;
        switch (user.getUiLanguage()) {
            case ENGLISH -> {
                if (user.getUiLanguage() == foundUser.getUiLanguage()) {
                    score += 40;
                }
            }
            case UKRAINIAN -> {
                if (foundUser.getUiLanguage() == LanguageENUM.UKRAINIAN) {
                    score += 30;
                }
            }
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
        return score;
    }
}
