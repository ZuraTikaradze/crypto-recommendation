package com.xm.cryptorecommendation.parser;


import com.xm.cryptorecommendation.entity.PriceEntity;
import com.xm.cryptorecommendation.exception.CSVParseException;
import com.xm.cryptorecommendation.model.CSVRecordWrapper;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Service
public class PriceDataParser implements CSVDataParser<PriceEntity> {
    @Override
    public PriceEntity parse(CSVRecordWrapper csvRecordWrapper) throws CSVParseException {
        return buildPriceEntity(csvRecordWrapper.getCsvRecord());
    }

    public PriceEntity buildPriceEntity(CSVRecord csvRecord) {
        return new PriceEntity().builder()
                .price(new BigDecimal(csvRecord.get("price")))
                .priceTimestamp(Instant.ofEpochMilli(Long.parseLong(csvRecord.get("timestamp"))))
                .build();
    }

    public Set<PriceEntity> buildPriceSet(Iterable<CSVRecord> records) {
        Set<PriceEntity> priceSet = new HashSet<>();
        for (CSVRecord csvRecord : records) {
            priceSet.add(buildPriceEntity(csvRecord));
        }
        return priceSet;
    }
}
