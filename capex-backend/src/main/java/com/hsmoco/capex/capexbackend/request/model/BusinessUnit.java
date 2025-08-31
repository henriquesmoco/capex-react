package com.hsmoco.capex.capexbackend.request.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_business_units")
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class BusinessUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "businessUnit")
    private List<Request> children = new ArrayList<>();

    public BusinessUnit(String name) {
        this.name = name;
    }
}
