package com.xm.cryptorecommendation.validator;

public interface Validator<T> {

    boolean isValid(T source);

    default boolean isInvalid(T source) {
        return !isValid(source);
    }
}
