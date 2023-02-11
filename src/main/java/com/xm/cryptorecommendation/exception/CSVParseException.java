package com.xm.cryptorecommendation.exception;

public class CSVParseException extends RuntimeException {

    public CSVParseException(String message) {
        super(message);
    }

    public CSVParseException(String message, Object... args) {
        super(String.format(message, args));
    }
}
