package com.rra.vehicletracking.service;

import com.rra.vehicletracking.dto.VehicleOwnershipHistoryDTOs.OwnershipHistoryResponse;
import com.rra.vehicletracking.entity.Vehicle;
import com.rra.vehicletracking.entity.VehicleOwnershipHistory;
import com.rra.vehicletracking.repository.VehicleOwnershipHistoryRepository;
import com.rra.vehicletracking.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class VehicleOwnershipHistoryService {

    @Autowired
    private VehicleOwnershipHistoryRepository ownershipHistoryRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    public Page<OwnershipHistoryResponse> getHistoryByChassisNumber(String chassisNumber, Pageable pageable) {
        Page<VehicleOwnershipHistory> history = ownershipHistoryRepository.findByVehicleChassisNumber(chassisNumber, pageable);
        return history.map(this::mapToResponse);
    }

    public Page<OwnershipHistoryResponse> getHistoryByPlateNumber(String plateNumber, Pageable pageable) {
        Page<VehicleOwnershipHistory> history = ownershipHistoryRepository.findByVehiclePlateNumber(plateNumber, pageable);
        return history.map(this::mapToResponse);
    }

    public Page<OwnershipHistoryResponse> getHistoryByVehicleId(Long vehicleId, Pageable pageable) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("Vehicle not found with id: " + vehicleId));

        Page<VehicleOwnershipHistory> history = ownershipHistoryRepository.findByVehicle(vehicle, pageable);
        return history.map(this::mapToResponse);
    }

    private OwnershipHistoryResponse mapToResponse(VehicleOwnershipHistory history) {
        return new OwnershipHistoryResponse(
                history.getId(),
                history.getVehicle().getId(),
                history.getVehicle().getChassisNumber(),
                history.getOwner().getOwnerNames(),
                history.getOwner().getNationalId(),
                history.getStartDate(),
                history.getEndDate(),
                history.getPurchasePrice(),
                history.getPreviousPlateNumber(),
                history.getComments()
        );
    }
}