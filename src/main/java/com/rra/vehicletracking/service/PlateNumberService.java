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

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlateNumberService {

    @Autowired
    private PlateNumberRepository plateNumberRepository;

    @Autowired
    private VehicleOwnerRepository vehicleOwnerRepository;

    public PlateNumberResponse registerPlateNumber(PlateNumberRequest request) {
        if (plateNumberRepository.existsByPlateNumber(request.getPlateNumber())) {
            throw new RuntimeException("Plate number already exists");
        }

        VehicleOwner owner = vehicleOwnerRepository.findById(request.getOwnerId())
                .orElseThrow(() -> new RuntimeException("Vehicle owner not found with id: " + request.getOwnerId()));

        PlateNumber plateNumber = new PlateNumber();
        plateNumber.setPlateNumber(request.getPlateNumber());
        plateNumber.setIssuedDate(request.getIssuedDate() != null ? request.getIssuedDate() : LocalDate.now());
        plateNumber.setVehicleOwner(owner); // Changed from setOwner to setVehicleOwner
        plateNumber.setStatus(PlateNumber.PlateStatus.AVAILABLE);

        PlateNumber savedPlateNumber = plateNumberRepository.save(plateNumber);

        // Add the plate number to the owner's collection
        owner.addPlateNumber(savedPlateNumber);
        vehicleOwnerRepository.save(owner);

        return mapToResponse(savedPlateNumber);
    }

    public Page<PlateNumberResponse> getPlateNumbersByOwner(Long ownerId, Pageable pageable) {
        VehicleOwner owner = vehicleOwnerRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Vehicle owner not found with id: " + ownerId));

        Page<PlateNumber> plateNumbers = plateNumberRepository.findByVehicleOwner(owner, pageable); // Changed from findByOwner to findByVehicleOwner
        return plateNumbers.map(this::mapToResponse);
    }

    public List<PlateNumberResponse> getAvailablePlateNumbersByOwner(Long ownerId) {
        VehicleOwner owner = vehicleOwnerRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Vehicle owner not found with id: " + ownerId));

        List<PlateNumber> availablePlateNumbers = plateNumberRepository.findByVehicleOwnerAndStatus(owner, PlateNumber.PlateStatus.AVAILABLE); // Updated query method
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
                plateNumber.getVehicleOwner().getId(), // Changed from getOwner to getVehicleOwner
                plateNumber.getVehicleOwner().getOwnerNames(), // Changed from getOwner to getVehicleOwner
                plateNumber.getStatus()
        );
    }
}