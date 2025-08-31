package com.hsmoco.capex.capexbackend.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_users")
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private String name;
}
