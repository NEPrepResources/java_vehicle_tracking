package com.rra.vehicletracking.service;

import com.rra.vehicletracking.dto.VehicleOwnerDTOs.OwnerRequest;
import com.rra.vehicletracking.dto.VehicleOwnerDTOs.OwnerResponse;
import com.rra.vehicletracking.entity.VehicleOwner;
import com.rra.vehicletracking.repository.VehicleOwnerRepository;
import com.rra.vehicletracking.repository.PlateNumberRepository;
import com.rra.vehicletracking.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VehicleOwnerService {

    @Autowired
    private VehicleOwnerRepository vehicleOwnerRepository;

    @Autowired
    private PlateNumberRepository plateNumberRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Transactional
    public OwnerResponse createVehicleOwner(OwnerRequest request) {
        if (vehicleOwnerRepository.existsByNationalId(request.getNationalId())) {
            throw new RuntimeException("Vehicle owner with this National ID already exists");
        }

        VehicleOwner owner = new VehicleOwner();
        owner.setOwnerNames(request.getOwnerNames());
        owner.setNationalId(request.getNationalId());
        owner.setPhoneNumber(request.getPhoneNumber());
        owner.setAddress(request.getAddress());

        VehicleOwner savedOwner = vehicleOwnerRepository.save(owner);
        return mapToResponse(savedOwner);
    }

    @Transactional
    public OwnerResponse updateVehicleOwner(Long id, OwnerRequest request) {
        VehicleOwner owner = vehicleOwnerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle owner not found with id: " + id));

        // Check if the new national ID already exists (and isn't the current one)
        if (!owner.getNationalId().equals(request.getNationalId()) &&
                vehicleOwnerRepository.existsByNationalId(request.getNationalId())) {
            throw new RuntimeException("Vehicle owner with this National ID already exists");
        }

        // Update fields
        owner.setOwnerNames(request.getOwnerNames());
        owner.setNationalId(request.getNationalId());
        owner.setPhoneNumber(request.getPhoneNumber());
        owner.setAddress(request.getAddress());

        VehicleOwner updatedOwner = vehicleOwnerRepository.save(owner);
        return mapToResponse(updatedOwner);
    }

    @Transactional
    public void deleteVehicleOwner(Long id) {
        VehicleOwner owner = vehicleOwnerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle owner not found with id: " + id));

        // Check if the owner has any associated plate numbers or vehicles
        if (!plateNumberRepository.findByVehicleOwnerAndStatus(owner, null).isEmpty()) {
            throw new RuntimeException("Cannot delete vehicle owner with associated plate numbers");
        }
        if (!vehicleRepository.findByCurrentOwner(owner, Pageable.unpaged()).isEmpty()) {
            throw new RuntimeException("Cannot delete vehicle owner with associated vehicles");
        }

        vehicleOwnerRepository.delete(owner);
    }

    public OwnerResponse getVehicleOwnerById(Long id) {
        VehicleOwner owner = vehicleOwnerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle owner not found with id: " + id));
        return mapToResponse(owner);
    }

    public OwnerResponse getVehicleOwnerByNationalId(String nationalId) {
        VehicleOwner owner = vehicleOwnerRepository.findByNationalId(nationalId)
                .orElseThrow(() -> new RuntimeException("Vehicle owner not found with National ID: " + nationalId));
        return mapToResponse(owner);
    }

    public Page<OwnerResponse> searchVehicleOwners(String nationalId, String phoneNumber, Pageable pageable) {
        Page<VehicleOwner> owners;

        if (nationalId != null && !nationalId.isEmpty()) {
            owners = vehicleOwnerRepository.findByNationalIdContaining(nationalId, pageable);
        } else if (phoneNumber != null && !phoneNumber.isEmpty()) {
            owners = vehicleOwnerRepository.findByPhoneNumberContaining(phoneNumber, pageable);
        } else {
            owners = vehicleOwnerRepository.findAll(pageable);
        }

        return owners.map(this::mapToResponse);
    }

    private OwnerResponse mapToResponse(VehicleOwner owner) {
        return new OwnerResponse(
                owner.getId(),
                owner.getOwnerNames(),
                owner.getNationalId(),
                owner.getPhoneNumber(),
                owner.getAddress()
        );
    }
}