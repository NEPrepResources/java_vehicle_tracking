package com.rra.vehicletracking.service;

import com.rra.vehicletracking.dto.VehicleOwnerDTOs.OwnerRequest;
import com.rra.vehicletracking.dto.VehicleOwnerDTOs.OwnerResponse;
import com.rra.vehicletracking.entity.VehicleOwner;
import com.rra.vehicletracking.repository.VehicleOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class VehicleOwnerService {

    @Autowired
    private VehicleOwnerRepository vehicleOwnerRepository;

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