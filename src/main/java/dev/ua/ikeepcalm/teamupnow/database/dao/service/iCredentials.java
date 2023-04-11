package dev.ua.ikeepcalm.teamupnow.database.dao.service;

import java.util.List;

public interface iCredentials<T> {

    T findByAccountId(long accountId);

    void deleteCredentials(long accountId);

    List<T> findAll();
}
