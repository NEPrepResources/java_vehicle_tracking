package com.rra.vehicletracking.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "plate_numbers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlateNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(nullable = false, unique = true)
    private String plateNumber;

    @Column(nullable = false)
    private LocalDate issuedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id",  nullable = false)
    private  VehicleOwner owner;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PlateStatus status;

    @OneToOne(mappedBy = "plateNumber", fetch = FetchType.LAZY)
    private  Vehicle vehicle;

    public  enum PlateStatus{
        AVAILABLE,
        IN_USE
    }


}
