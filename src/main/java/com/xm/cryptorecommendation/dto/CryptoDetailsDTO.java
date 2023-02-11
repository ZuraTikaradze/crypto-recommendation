package com.xm.cryptorecommendation.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CryptoDetailsDTO {
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private BigDecimal newestPrice;
    private BigDecimal oldestPrice;
}
