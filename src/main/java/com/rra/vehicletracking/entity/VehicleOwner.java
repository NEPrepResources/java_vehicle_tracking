package com.rra.vehicletracking.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.ArrayList;


@Entity
@Table(name = "vehicle_owner")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private  String ownerNames;

    @Column(nullable = false, unique = true)
    private  String nationalId;

    @Column(nullable = false)
    private  String phoneNumber;

    @Column(nullable = false)
    private  String address;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private  List<PlateNumber> plateNumbers = new ArrayList<>();

    public void addPlateNumber(PlateNumber plateNumber){
        plateNumbers.add(plateNumber);
        plateNumber.setOwner(this);
    }

    public  void removePlateNumber(PlateNumber plateNumber){
        plateNumbers.remove(plateNumber);
        plateNumber.setOwner(null);
    }


}
