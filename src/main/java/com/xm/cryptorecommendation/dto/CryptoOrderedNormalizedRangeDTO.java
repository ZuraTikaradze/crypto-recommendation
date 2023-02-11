package com.xm.cryptorecommendation.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CryptoOrderedNormalizedRangeDTO {
    List<CryptoDTO> cryptos;
}
