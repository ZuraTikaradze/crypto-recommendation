package com.xm.cryptorecommendation.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "crypto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Validated
@Builder
public class CryptoEntity extends BaseEntity {

    /**
     * Unique id
     */
    @NotNull
    @Id
    @SequenceGenerator(name = "crypto_id_gen", sequenceName = "crypto_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "crypto_id_gen")
    private Long id;

    /**
     * Crypto name
     */
    @NotNull
    private String name;

    /**
     * Crypto active status
     */
    @NotNull
    private boolean active;


    /**
     * Crypto prices
     */
    @OneToMany(mappedBy = "crypto", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PriceEntity> prices = new HashSet<>();
}
