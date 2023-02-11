package com.xm.cryptorecommendation.service.impl;

import com.xm.cryptorecommendation.entity.CryptoEntity;
import com.xm.cryptorecommendation.exception.NotFoundException;
import com.xm.cryptorecommendation.repository.CryptoRepository;
import com.xm.cryptorecommendation.service.CryptoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CryptoServiceImpl implements CryptoService {

    private final CryptoRepository cryptoRepository;

    public CryptoServiceImpl(CryptoRepository cryptoRepository) {
        this.cryptoRepository = cryptoRepository;
    }

    @Override
    public CryptoEntity getActiveCrypto(String cryptoName) {
        return cryptoRepository.getByActiveIsTrue().stream()
                .filter(c -> c.getName().equals(cryptoName))
                .findAny()
                .orElseThrow(() -> new NotFoundException("Crypto not found"));
    }

    @Override
    public List<CryptoEntity> getActiveCryptos() {
        return cryptoRepository.getByActiveIsTrue();
    }


}
