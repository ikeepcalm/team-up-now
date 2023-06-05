package dev.ua.ikeepcalm.teamupnow.database.exceptions;

import org.slf4j.LoggerFactory;

public class DAOException extends RuntimeException{
    public DAOException(String message) {
        LoggerFactory.getLogger(getClass()).warn(message);
    }
}
