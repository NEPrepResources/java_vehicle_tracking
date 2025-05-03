package com.rra.vehicletracking.repository;

import com.rra.vehicletracking.entity.VehicleOwner;
import com.rra.vehicletracking.entity.PlateNumber;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlateNumberRepository extends JpaRepository<PlateNumber, Long>{
    Optional<PlateNumber> findByPlateNumber(String plateNumber);
    Page<PlateNumber> findByOwner(VehicleOwner owner, Pageable pageable);

    @Query("SELECT p FROM PlateNumber p WHERE p.owner = ?1 AND p.status = 'AVAILABLE'")
    List<PlateNumber> findAvailablePlateNumbers(VehicleOwner owner);

    boolean existsByPlateNumber(String plateNumber);
}
