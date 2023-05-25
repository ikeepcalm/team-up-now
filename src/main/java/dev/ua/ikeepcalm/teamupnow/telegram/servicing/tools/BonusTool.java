package dev.ua.ikeepcalm.teamupnow.telegram.servicing.tools;


import dev.ua.ikeepcalm.teamupnow.database.dao.service.impls.BonusService;
import dev.ua.ikeepcalm.teamupnow.database.entities.Bonus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class BonusTool {

    private final BonusService bonusService;

    @Autowired
    public BonusTool(BonusService bonusService) {
        this.bonusService = bonusService;
    }

    public String generateBonusCode(){
        UUID code = UUID.randomUUID();
        Bonus bonus = new Bonus();
        bonus.setValidated(false);
        bonus.setCode(code.toString());
        bonusService.save(bonus);
        return code.toString();
    }
}
