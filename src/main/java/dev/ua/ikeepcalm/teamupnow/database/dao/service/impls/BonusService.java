package dev.ua.ikeepcalm.teamupnow.database.dao.service.impls;

import dev.ua.ikeepcalm.teamupnow.database.dao.repo.BonusRepo;
import dev.ua.ikeepcalm.teamupnow.database.dao.repo.PersistentRepo;
import dev.ua.ikeepcalm.teamupnow.database.entities.Bonus;
import dev.ua.ikeepcalm.teamupnow.database.entities.Persistent;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BonusService {

    private final BonusRepo bonusRepo;

    public BonusService( BonusRepo bonusRepo) {
        this.bonusRepo = bonusRepo;
    }

    public void save(Bonus bonus){
        bonusRepo.save(bonus);
    }

    public boolean validate(String code){
        Optional<Bonus> b = bonusRepo.findByCode(code);
        if (b.isPresent()){
            Bonus bs = b.get();
            if (bs.isValidated()){
                return false;
            } else {
                bs.setValidated(true);
                save(bs);
                return true;
            }
        } else {
            return false;
        }
    }
}
