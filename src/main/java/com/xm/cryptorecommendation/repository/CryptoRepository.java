package com.xm.cryptorecommendation.repository;

import com.xm.cryptorecommendation.entity.CryptoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CryptoRepository extends JpaRepository<CryptoEntity, Long> {
    List<CryptoEntity> getByActiveIsTrue();
}
