package com.xm.cryptorecommendation.entity;

import jakarta.persistence.*;
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
    @Id
    @SequenceGenerator(name = "crypto_id_gen", sequenceName = "crypto_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "crypto_id_gen")
    private Long id;
    private String name;
    private boolean active;

    @OneToMany(mappedBy = "crypto", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PriceEntity> prices = new HashSet<>();
}
