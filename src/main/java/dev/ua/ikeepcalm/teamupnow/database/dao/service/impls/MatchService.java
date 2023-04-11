package dev.ua.ikeepcalm.teamupnow.database.dao.service.impls;

import dev.ua.ikeepcalm.teamupnow.database.dao.repo.MatchRepo;
import dev.ua.ikeepcalm.teamupnow.database.entities.Credentials;
import dev.ua.ikeepcalm.teamupnow.database.entities.Match;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.GameENUM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class MatchService {

    @Autowired
    private MatchRepo matchRepo;
    @Autowired
    private CredentialsService credentialsService;

    private void createMatchesForUser(Credentials user){
        List<Credentials> credentialsList = credentialsService.findAll();
        for (Credentials foundUser : credentialsList) {
            Match match = new Match();
            match.setFirstUserId(user.getAccountId());
            match.setSecondUserId(foundUser.getAccountId());
            {
                if (user.getUiLanguage() == foundUser.getUiLanguage()){
                    match.setScore(40);
                }
            }//Language
            {
                GameENUM[] arr1 = user.getGames().toArray(new GameENUM[0]);
                GameENUM[] arr2 = foundUser.getGames().toArray(new GameENUM[0]);
                Arrays.sort(arr1);
                Arrays.sort(arr2);

                int matches = 0;
                int total = Math.max(arr1.length, arr2.length);
                for(int i = 0, j = 0; i < arr1.length && j < arr2.length;) {
                    if(arr1[i].equals(arr2[j])) {
                        matches++;
                        i++;
                        j++;
                    } else if(arr1[i].compareTo(arr2[j]) < 0) {
                        i++;
                    } else {
                        j++;
                    }
                }

                double similarityPercentage = (double) matches / total;
                match.setScore((int) (match.getScore() + 40 * similarityPercentage));
            }//Games
            {
                if (user.getDemographic().getAge() == foundUser.getDemographic().getAge()){
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

    private List<Match> findMatchesForUser(Credentials user){
        return matchRepo.findByFirstUserAndDescScore(user.getAccountId());
    }
}
