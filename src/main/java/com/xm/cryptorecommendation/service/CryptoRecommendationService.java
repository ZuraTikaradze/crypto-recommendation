package com.xm.cryptorecommendation.service;

import com.xm.cryptorecommendation.dto.CryptoDTO;
import com.xm.cryptorecommendation.dto.CryptoDetailsDTO;
import com.xm.cryptorecommendation.dto.CryptoOrderedNormalizedRangeDTO;

import java.time.LocalDate;

public interface CryptoRecommendationService {
    CryptoDetailsDTO getCryptoDetails(String cryptoName, LocalDate from, LocalDate to);

    CryptoDTO getHighestNormalizedRangeCryptoForSpecificDay(LocalDate date);

    CryptoOrderedNormalizedRangeDTO getNormalizedCryptosOrderedByPrice(LocalDate from, LocalDate to);

}
