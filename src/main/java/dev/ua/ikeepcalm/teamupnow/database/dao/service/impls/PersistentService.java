package dev.ua.ikeepcalm.teamupnow.database.dao.service.impls;

import dev.ua.ikeepcalm.teamupnow.database.dao.repo.PersistentRepo;
import dev.ua.ikeepcalm.teamupnow.database.entities.Persistent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersistentService {

    @Autowired
    private PersistentRepo persistentRepo;

    public Persistent findPersistent(){
        return persistentRepo.findById(1L).get();
    }

    public void save(Persistent persistent){
        persistentRepo.save(persistent);
    }

}
