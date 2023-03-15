package dev.ua.ikeepcalm.teamupnow.database.exceptions;

import lombok.experimental.StandardException;


public class DAOException extends RuntimeException{
    public DAOException(String message) {
        super(message);
    }
}
