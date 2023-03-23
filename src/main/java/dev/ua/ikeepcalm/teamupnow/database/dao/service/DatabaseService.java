package dev.ua.ikeepcalm.teamupnow.database.dao.service;

public interface DatabaseService<T> {

    T findByAccountId(long accountId);

    void deleteCredentials(long accountId);

}
