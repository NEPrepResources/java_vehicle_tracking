package com.rra.vehicletracking.service;

import com.rra.vehicletracking.dto.VehicleOwnershipHistoryDTOs.OwnershipHistoryResponse;
import com.rra.vehicletracking.entity.Vehicle;
import com.rra.vehicletracking.entity.VehicleOwner;
import com.rra.vehicletracking.entity.VehicleOwnershipHistory;
import com.rra.vehicletracking.repository.VehicleOwnershipHistoryRepository;
import com.rra.vehicletracking.repository.VehicleRepository;
import com.rra.vehicletracking.repository.VehicleOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VehicleOwnershipHistoryService {

    @Autowired
    private VehicleOwnershipHistoryRepository ownershipHistoryRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private VehicleOwnerRepository vehicleOwnerRepository;

    @Transactional
    public OwnershipHistoryResponse createOwnershipHistory(OwnershipHistoryResponse request) {
        Vehicle vehicle = vehicleRepository.findById(request.getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found with id: " + request.getVehicleId()));

        VehicleOwner owner = vehicleOwnerRepository.findByNationalId(request.getOwnerNationalId())
                .orElseThrow(() -> new RuntimeException("Owner not found with national ID: " + request.getOwnerNationalId()));

        VehicleOwnershipHistory history = new VehicleOwnershipHistory();
        history.setVehicle(vehicle);
        history.setOwner(owner);
        history.setStartDate(request.getStartDate());
        history.setEndDate(request.getEndDate());
        history.setPurchasePrice(request.getPurchasePrice());
        history.setPreviousPlateNumber(request.getPlateNumber());
        history.setComments(request.getComments());

        VehicleOwnershipHistory savedHistory = ownershipHistoryRepository.save(history);
        return mapToResponse(savedHistory);
    }

    @Transactional
    public OwnershipHistoryResponse updateOwnershipHistory(Long id, OwnershipHistoryResponse request) {
        VehicleOwnershipHistory history = ownershipHistoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ownership history not found with id: " + id));

        Vehicle vehicle = vehicleRepository.findById(request.getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found with id: " + request.getVehicleId()));

        VehicleOwner owner = vehicleOwnerRepository.findByNationalId(request.getOwnerNationalId())
                .orElseThrow(() -> new RuntimeException("Owner not found with national ID: " + request.getOwnerNationalId()));

        // Update fields
        history.setVehicle(vehicle);
        history.setOwner(owner);
        history.setStartDate(request.getStartDate());
        history.setEndDate(request.getEndDate());
        history.setPurchasePrice(request.getPurchasePrice());
        history.setPreviousPlateNumber(request.getPlateNumber());
        history.setComments(request.getComments());

        VehicleOwnershipHistory updatedHistory = ownershipHistoryRepository.save(history);
        return mapToResponse(updatedHistory);
    }

    @Transactional
    public void deleteOwnershipHistory(Long id) {
        VehicleOwnershipHistory history = ownershipHistoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ownership history not found with id: " + id));

        ownershipHistoryRepository.delete(history);
    }

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