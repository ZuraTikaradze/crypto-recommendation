package com.xm.cryptorecommendation.exception;

public class CSVFormatException extends RuntimeException{
    public CSVFormatException(String message) {
        super(message);
    }

    public CSVFormatException(String message, Object... args) {
        super(String.format(message, args));
    }
}
