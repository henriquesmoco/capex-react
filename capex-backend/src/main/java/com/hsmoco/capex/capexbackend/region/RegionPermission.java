package com.hsmoco.capex.capexbackend.region;

import com.hsmoco.capex.capexbackend.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_region_permissions")
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class RegionPermission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Region region;

    @Column(nullable = false)
    private boolean canView;

    @Column(nullable = false)
    private boolean canEdit;

}
