package com.xm.cryptorecommendation.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "price")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceEntity extends BaseEntity {

    /**
     * Unique id
     */
    @Id
    @SequenceGenerator(name = "price_id_gen", sequenceName = "price_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "price_id_gen")
    private Long id;

    /**
     * Price
     */
    @NotNull
    private BigDecimal price;

    /**
     * Price timestamp
     */
    @NotNull
    private Instant priceTimestamp;
    @Valid
    @ManyToOne(fetch = FetchType.LAZY)

    /**
     * Crypto
     */
    @Cascade(CascadeType.ALL)
    private CryptoEntity crypto;
}