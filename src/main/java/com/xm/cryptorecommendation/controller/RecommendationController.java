package com.xm.cryptorecommendation.controller;

import com.xm.cryptorecommendation.dto.CryptoDTO;
import com.xm.cryptorecommendation.dto.CryptoDetailsDTO;
import com.xm.cryptorecommendation.dto.CryptoOrderedNormalizedRangeDTO;
import com.xm.cryptorecommendation.service.CryptoRecommendationService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/recommendation")
public class RecommendationController {

    private final CryptoRecommendationService cryptoRecommendationService;

    public RecommendationController(CryptoRecommendationService cryptoRecommendationService) {
        this.cryptoRecommendationService = cryptoRecommendationService;
    }

    @GetMapping("{crypto}/details/{from}/{to}")
    public CryptoDetailsDTO getCryptoDetails(@PathVariable String crypto,
                                             @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from,
                                             @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate to) {

        return cryptoRecommendationService.getCryptoDetails(crypto, from, to);
    }

    @GetMapping("highest-normalized/{from}/{to}")
    public CryptoOrderedNormalizedRangeDTO getNormalizedCryptosOrderedByPrice(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate to) {

        return cryptoRecommendationService.getNormalizedCryptosOrderedByPrice(from, to);
    }

    @GetMapping("highest-normalized/{date}")
    public CryptoDTO getHighestNormalizedRangeCryptoForSpecificDay(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return cryptoRecommendationService.getHighestNormalizedRangeCryptoForSpecificDay(date);
    }

}
