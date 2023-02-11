package com.xm.cryptorecommendation.parser;

import com.xm.cryptorecommendation.exception.CSVParseException;
import com.xm.cryptorecommendation.model.CSVRecordWrapper;

public interface CSVDataParser<T> {
    T parse(CSVRecordWrapper csvRecordWrapper) throws CSVParseException;
}
