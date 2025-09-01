package com.hsmoco.capex.capexbackend.request.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_requests")
@NoArgsConstructor
@Data
@EqualsAndHashCode
@EntityListeners(RequestNumberEntityListener.class)
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "request_type",  nullable = false, updatable = false)
    private RequestType type;

    @Column(name = "request_number", nullable = false, updatable = false)
    private String requestNumber;

    @Column(name = "PROJECT_NAME", nullable = false)
    private String projectName;

    @Column(name = "PROJECT_DATE")
    @Temporal(TemporalType.DATE)
    private LocalDate projectDate;

    @Column(name = "capex_cost")
    private BigDecimal capexCost;

    @Column(name = "opex_cost")
    private BigDecimal opexCost;

    @Column(name = "description")
    private String description;

    @Column(name = "emergency")
    private Boolean emergency;

    @Column(name = "it_project")
    private Boolean itProject;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PARENT_REQUEST")
    private Request parent;

    @OneToMany(mappedBy = "parent")
    private List<Request> children =  new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "category")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "business_unit")
    private BusinessUnit businessUnit;
}
