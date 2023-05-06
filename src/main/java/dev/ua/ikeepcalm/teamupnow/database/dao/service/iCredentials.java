package dev.ua.ikeepcalm.teamupnow.database.dao.service;

import dev.ua.ikeepcalm.teamupnow.database.entities.Credentials;

import java.util.List;

public interface iCredentials<T> {

    T findByAccountId(long accountId);

    void deleteCredentials(long accountId);

    List<T> findAll();

    void saveAll(List<Credentials> list);

    List<Credentials> findAllExcept(Long excludedId);

    void save(Credentials credentials);

    void deleteGamesLinkedToCredentials(Credentials credentials);
}
