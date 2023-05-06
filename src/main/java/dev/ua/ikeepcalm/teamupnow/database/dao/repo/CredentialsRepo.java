package dev.ua.ikeepcalm.teamupnow.database.dao.repo;

import dev.ua.ikeepcalm.teamupnow.database.entities.Credentials;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CredentialsRepo extends CrudRepository<Credentials, Long> {
    Optional<Credentials> findCredentialsByAccountId(Long accountId);
    @Query("SELECT e FROM Credentials e WHERE e.accountId <> :excludedId")
    List<Credentials> findAllExcept(Long excludedId);
}
