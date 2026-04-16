package com.hsmoco.capex.capexbackend.region;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_regions")
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class Region {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code; // "NA", "CA", etc.
}
