package com.rra.vehicletracking.service;

import com.rra.vehicletracking.dto.PlateNumberDTOs.PlateNumberRequest;
import com.rra.vehicletracking.dto.PlateNumberDTOs.PlateNumberResponse;
import com.rra.vehicletracking.entity.PlateNumber;
import com.rra.vehicletracking.entity.VehicleOwner;
import com.rra.vehicletracking.repository.PlateNumberRepository;
import com.rra.vehicletracking.repository.VehicleOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlateNumberService {

    @Autowired
    private PlateNumberRepository plateNumberRepository;

    @Autowired
    private VehicleOwnerRepository vehicleOwnerRepository;

    @Transactional
    public PlateNumberResponse registerPlateNumber(PlateNumberRequest request) {
        if (plateNumberRepository.existsByPlateNumber(request.getPlateNumber())) {
            throw new RuntimeException("Plate number already exists");
        }

        VehicleOwner owner = vehicleOwnerRepository.findById(request.getOwnerId())
                .orElseThrow(() -> new RuntimeException("Vehicle owner not found with id: " + request.getOwnerId()));

        PlateNumber plateNumber = new PlateNumber();
        plateNumber.setPlateNumber(request.getPlateNumber());
        plateNumber.setIssuedDate(request.getIssuedDate() != null ? request.getIssuedDate() : LocalDate.now());
        plateNumber.setVehicleOwner(owner);
        plateNumber.setStatus(PlateNumber.PlateStatus.AVAILABLE);

        PlateNumber savedPlateNumber = plateNumberRepository.save(plateNumber);

        // Add the plate number to the owner's collection
        owner.addPlateNumber(savedPlateNumber);
        vehicleOwnerRepository.save(owner);

        return mapToResponse(savedPlateNumber);
    }

    @Transactional
    public PlateNumberResponse updatePlateNumber(Long id, PlateNumberRequest request) {
        PlateNumber plateNumber = plateNumberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plate number not found with id: " + id));

        // Check if the new plate number already exists (and isn't the current one)
        if (!plateNumber.getPlateNumber().equals(request.getPlateNumber()) &&
                plateNumberRepository.existsByPlateNumber(request.getPlateNumber())) {
            throw new RuntimeException("Plate number already exists");
        }

        VehicleOwner owner = vehicleOwnerRepository.findById(request.getOwnerId())
                .orElseThrow(() -> new RuntimeException("Vehicle owner not found with id: " + request.getOwnerId()));

        // Update fields
        plateNumber.setPlateNumber(request.getPlateNumber());
        plateNumber.setIssuedDate(request.getIssuedDate() != null ? request.getIssuedDate() : LocalDate.now());
        plateNumber.setVehicleOwner(owner);

        PlateNumber updatedPlateNumber = plateNumberRepository.save(plateNumber);
        return mapToResponse(updatedPlateNumber);
    }

    @Transactional
    public void deletePlateNumber(Long id) {
        PlateNumber plateNumber = plateNumberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plate number not found with id: " + id));

        if (plateNumber.getStatus() == PlateNumber.PlateStatus.IN_USE) {
            throw new RuntimeException("Cannot delete a plate number that is currently in use");
        }

        plateNumberRepository.delete(plateNumber);
    }

    public Page<PlateNumberResponse> getPlateNumbersByOwner(Long ownerId, Pageable pageable) {
        VehicleOwner owner = vehicleOwnerRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Vehicle owner not found with id: " + ownerId));

        Page<PlateNumber> plateNumbers = plateNumberRepository.findByVehicleOwner(owner, pageable);
        return plateNumbers.map(this::mapToResponse);
    }

    public List<PlateNumberResponse> getAvailablePlateNumbersByOwner(Long ownerId) {
        VehicleOwner owner = vehicleOwnerRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Vehicle owner not found with id: " + ownerId));

        List<PlateNumber> availablePlateNumbers = plateNumberRepository.findByVehicleOwnerAndStatus(owner, PlateNumber.PlateStatus.AVAILABLE);
        return availablePlateNumbers.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public PlateNumberResponse getPlateNumberById(Long id) {
        PlateNumber plateNumber = plateNumberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plate number not found with id: " + id));
        return mapToResponse(plateNumber);
    }

    public PlateNumberResponse getPlateNumberByPlateNumber(String plateNumberStr) {
        PlateNumber plateNumber = plateNumberRepository.findByPlateNumber(plateNumberStr)
                .orElseThrow(() -> new RuntimeException("Plate number not found: " + plateNumberStr));
        return mapToResponse(plateNumber);
    }

    private PlateNumberResponse mapToResponse(PlateNumber plateNumber) {
        return new PlateNumberResponse(
                plateNumber.getId(),
                plateNumber.getPlateNumber(),
                plateNumber.getIssuedDate(),
                plateNumber.getVehicleOwner().getId(),
                plateNumber.getVehicleOwner().getOwnerNames(),
                plateNumber.getStatus()
        );
    }
}