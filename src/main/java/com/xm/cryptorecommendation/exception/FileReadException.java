package com.xm.cryptorecommendation.exception;

public class FileReadException  extends RuntimeException{
    public FileReadException(String message) {
        super(message);
    }

    public FileReadException(String message, Object... args) {
        super(String.format(message, args));
    }
}
