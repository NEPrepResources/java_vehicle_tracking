package com.rra.vehicletracking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vehicles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String chassisNumber;

    @Column(nullable = false)
    private String manufactureCompany;

    @Column(nullable = false)
    private Integer manufactureYear;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private String modelName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plate_number_id")
    private PlateNumber plateNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "current_owner_id")
    private VehicleOwner currentOwner;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<VehicleOwnershipHistory> ownershipHistory = new ArrayList<>();
}