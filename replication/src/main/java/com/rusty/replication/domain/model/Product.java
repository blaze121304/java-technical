package com.rusty.replication.domain.model;


import com.rusty.replication.controller.enums.MeasurementUnit;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "product")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "measurement_unit")
    private MeasurementUnit measurementUnit;
}

