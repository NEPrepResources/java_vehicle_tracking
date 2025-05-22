package com.rra.vehicletracking.service;

import com.rra.vehicletracking.dto.VehicleDTOs.VehicleRegistrationRequest;
import com.rra.vehicletracking.dto.VehicleDTOs.VehicleResponse;
import com.rra.vehicletracking.dto.VehicleDTOs.VehicleTransferRequest;
import com.rra.vehicletracking.entity.PlateNumber;
import com.rra.vehicletracking.entity.Vehicle;
import com.rra.vehicletracking.entity.VehicleOwner;
import com.rra.vehicletracking.entity.VehicleOwnershipHistory;
import com.rra.vehicletracking.repository.PlateNumberRepository;
import com.rra.vehicletracking.repository.VehicleOwnerRepository;
import com.rra.vehicletracking.repository.VehicleOwnershipHistoryRepository;
import com.rra.vehicletracking.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private VehicleOwnerRepository vehicleOwnerRepository;

    @Autowired
    private PlateNumberRepository plateNumberRepository;

    @Autowired
    private VehicleOwnershipHistoryRepository ownershipHistoryRepository;

    @Transactional
    public VehicleResponse registerVehicle(VehicleRegistrationRequest request) {
        if (vehicleRepository.existsByChassisNumber(request.getChassisNumber())) {
            throw new RuntimeException("Vehicle with this chassis number already exists");
        }

        VehicleOwner owner = vehicleOwnerRepository.findById(request.getOwnerId())
                .orElseThrow(() -> new RuntimeException("Vehicle owner not found with id: " + request.getOwnerId()));

        PlateNumber plateNumber = plateNumberRepository.findByPlateNumber(request.getPlateNumber())
                .orElseThrow(() -> new RuntimeException("Plate number not found: " + request.getPlateNumber()));

        if (!plateNumber.getVehicleOwner().getId().equals(owner.getId())) {
            throw new RuntimeException("Plate number does not belong to this owner");
        }

        if (plateNumber.getStatus() != PlateNumber.PlateStatus.AVAILABLE) {
            throw new RuntimeException("Plate number is already in use");
        }

        Vehicle vehicle = new Vehicle();
        vehicle.setChassisNumber(request.getChassisNumber());
        vehicle.setManufactureCompany(request.getManufactureCompany());
        vehicle.setManufactureYear(request.getManufactureYear());
        vehicle.setPrice(request.getPrice());
        vehicle.setModelName(request.getModelName());
        vehicle.setCurrentOwner(owner);
        vehicle.setPlateNumber(plateNumber);

        Vehicle savedVehicle = vehicleRepository.save(vehicle);

        plateNumber.setStatus(PlateNumber.PlateStatus.IN_USE);
        plateNumber.setVehicle(savedVehicle);
        plateNumberRepository.save(plateNumber);

        VehicleOwnershipHistory ownershipHistory = new VehicleOwnershipHistory();
        ownershipHistory.setVehicle(savedVehicle);
        ownershipHistory.setOwner(owner);
        ownershipHistory.setStartDate(LocalDateTime.now());
        ownershipHistory.setPurchasePrice(request.getPrice());
        ownershipHistory.setComments("Initial registration");
        ownershipHistoryRepository.save(ownershipHistory);

        return mapToResponse(savedVehicle);
    }

    @Transactional
    public VehicleResponse updateVehicle(Long id, VehicleRegistrationRequest request) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found with id: " + id));

        // Check if the new chassis number already exists (and isn't the current one)
        if (!vehicle.getChassisNumber().equals(request.getChassisNumber()) &&
                vehicleRepository.existsByChassisNumber(request.getChassisNumber())) {
            throw new RuntimeException("Vehicle with this chassis number already exists");
        }

        VehicleOwner owner = vehicleOwnerRepository.findById(request.getOwnerId())
                .orElseThrow(() -> new RuntimeException("Vehicle owner not found with id: " + request.getOwnerId()));

        PlateNumber newPlateNumber = plateNumberRepository.findByPlateNumber(request.getPlateNumber())
                .orElseThrow(() -> new RuntimeException("Plate number not found: " + request.getPlateNumber()));

        if (!newPlateNumber.getVehicleOwner().getId().equals(owner.getId())) {
            throw new RuntimeException("Plate number does not belong to this owner");
        }

        if (newPlateNumber.getStatus() != PlateNumber.PlateStatus.AVAILABLE) {
            throw new RuntimeException("Plate number is already in use");
        }

        // Update the old plate number status
        PlateNumber oldPlateNumber = vehicle.getPlateNumber();
        if (!oldPlateNumber.getPlateNumber().equals(newPlateNumber.getPlateNumber())) {
            oldPlateNumber.setStatus(PlateNumber.PlateStatus.AVAILABLE);
            oldPlateNumber.setVehicle(null);
            plateNumberRepository.save(oldPlateNumber);

            newPlateNumber.setStatus(PlateNumber.PlateStatus.IN_USE);
            newPlateNumber.setVehicle(vehicle);
            plateNumberRepository.save(newPlateNumber);
        }

        // Update vehicle fields
        vehicle.setChassisNumber(request.getChassisNumber());
        vehicle.setManufactureCompany(request.getManufactureCompany());
        vehicle.setManufactureYear(request.getManufactureYear());
        vehicle.setPrice(request.getPrice());
        vehicle.setModelName(request.getModelName());
        vehicle.setCurrentOwner(owner);
        vehicle.setPlateNumber(newPlateNumber);

        Vehicle updatedVehicle = vehicleRepository.save(vehicle);
        return mapToResponse(updatedVehicle);
    }

    @Transactional
    public void deleteVehicle(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found with id: " + id));

        // Update the plate number status
        PlateNumber plateNumber = vehicle.getPlateNumber();
        plateNumber.setStatus(PlateNumber.PlateStatus.AVAILABLE);
        plateNumber.setVehicle(null);
        plateNumberRepository.save(plateNumber);

        // Delete associated ownership history
        ownershipHistoryRepository.deleteAll(ownershipHistoryRepository.findByVehicle(vehicle, Pageable.unpaged()).getContent());

        vehicleRepository.delete(vehicle);
    }

    @Transactional
    public VehicleResponse transferVehicle(VehicleTransferRequest request) {
        Vehicle vehicle = null;
        if (request.getVehicleIdentifier().matches("^[A-Z0-9]{6,17}$")) {
            vehicle = vehicleRepository.findByChassisNumber(request.getVehicleIdentifier())
                    .orElseThrow(() -> new RuntimeException("Vehicle not found with chassis number: " + request.getVehicleIdentifier()));
        } else {
            vehicle = vehicleRepository.findByPlateNumberPlateNumber(request.getVehicleIdentifier())
                    .orElseThrow(() -> new RuntimeException("Vehicle not found with plate number: " + request.getVehicleIdentifier()));
        }

        VehicleOwner newOwner = vehicleOwnerRepository.findById(request.getNewOwnerId())
                .orElseThrow(() -> new RuntimeException("New owner not found with id: " + request.getNewOwnerId()));

        PlateNumber oldPlateNumber = vehicle.getPlateNumber();
        String oldPlateNumberValue = oldPlateNumber.getPlateNumber();

        PlateNumber newPlateNumber = plateNumberRepository.findByPlateNumber(request.getNewPlateNumber())
                .orElseThrow(() -> new RuntimeException("New plate number not found: " + request.getNewPlateNumber()));

        if (!newPlateNumber.getVehicleOwner().getId().equals(newOwner.getId())) {
            throw new RuntimeException("New plate number does not belong to the new owner");
        }

        if (newPlateNumber.getStatus() != PlateNumber.PlateStatus.AVAILABLE) {
            throw new RuntimeException("New plate number is already in use");
        }

        VehicleOwnershipHistory latestHistory = ownershipHistoryRepository.findByVehicleOrderByStartDateDesc(vehicle)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No ownership history found for this vehicle"));
        latestHistory.setEndDate(LocalDateTime.now());
        ownershipHistoryRepository.save(latestHistory);

        VehicleOwnershipHistory newOwnershipHistory = new VehicleOwnershipHistory();
        newOwnershipHistory.setVehicle(vehicle);
        newOwnershipHistory.setOwner(newOwner);
        newOwnershipHistory.setStartDate(LocalDateTime.now());
        newOwnershipHistory.setPurchasePrice(request.getPurchasePrice());
        newOwnershipHistory.setPreviousPlateNumber(oldPlateNumberValue);
        newOwnershipHistory.setComments(request.getComments());
        ownershipHistoryRepository.save(newOwnershipHistory);

        oldPlateNumber.setStatus(PlateNumber.PlateStatus.AVAILABLE);
        oldPlateNumber.setVehicle(null);
        plateNumberRepository.save(oldPlateNumber);

        newPlateNumber.setStatus(PlateNumber.PlateStatus.IN_USE);
        newPlateNumber.setVehicle(vehicle);
        plateNumberRepository.save(newPlateNumber);

        vehicle.setCurrentOwner(newOwner);
        vehicle.setPlateNumber(newPlateNumber);
        Vehicle updatedVehicle = vehicleRepository.save(vehicle);

        return mapToResponse(updatedVehicle);
    }

    public VehicleResponse getVehicleByChassisNumber(String chassisNumber) {
        Vehicle vehicle = vehicleRepository.findByChassisNumber(chassisNumber)
                .orElseThrow(() -> new RuntimeException("Vehicle not found with chassis number: " + chassisNumber));
        return mapToResponse(vehicle);
    }

    public VehicleResponse getVehicleByPlateNumber(String plateNumber) {
        Vehicle vehicle = vehicleRepository.findByPlateNumberPlateNumber(plateNumber)
                .orElseThrow(() -> new RuntimeException("Vehicle not found with plate number: " + plateNumber));
        return mapToResponse(vehicle);
    }

    public Page<VehicleResponse> getVehiclesByOwner(Long ownerId, Pageable pageable) {
        VehicleOwner owner = vehicleOwnerRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Vehicle owner not found with id: " + ownerId));

        Page<Vehicle> vehicles = vehicleRepository.findByCurrentOwner(owner, pageable);
        return vehicles.map(this::mapToResponse);
    }

    private VehicleResponse mapToResponse(Vehicle vehicle) {
        return new VehicleResponse(
                vehicle.getId(),
                vehicle.getChassisNumber(),
                vehicle.getManufactureCompany(),
                vehicle.getManufactureYear(),
                vehicle.getPrice(),
                vehicle.getModelName(),
                vehicle.getPlateNumber().getPlateNumber(),
                vehicle.getCurrentOwner().getOwnerNames(),
                vehicle.getCurrentOwner().getNationalId()
        );
    }
}