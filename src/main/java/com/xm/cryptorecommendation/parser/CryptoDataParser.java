package com.xm.cryptorecommendation.parser;

import com.xm.cryptorecommendation.entity.CryptoEntity;
import com.xm.cryptorecommendation.exception.CSVParseException;
import com.xm.cryptorecommendation.model.CSVRecordWrapper;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

@Service
public class CryptoDataParser implements CSVDataParser<CryptoEntity> {
    @Override
    public CryptoEntity parse(CSVRecordWrapper csvRecordWrapper) throws CSVParseException {
        return buildCryptoEntity(csvRecordWrapper.getCsvRecord());
    }

    public CryptoEntity buildCryptoEntity(CSVRecord csvRecord) {
        return  new CryptoEntity().builder()
                .name(csvRecord.get("symbol"))
                .build();
    }
}
