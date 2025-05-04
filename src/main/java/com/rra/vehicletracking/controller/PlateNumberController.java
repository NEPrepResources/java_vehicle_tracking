package com.rra.vehicletracking.controller;

import com.rra.vehicletracking.dto.PlateNumberDTOs.PlateNumberRequest;
import com.rra.vehicletracking.dto.PlateNumberDTOs.PlateNumberResponse;
import com.rra.vehicletracking.service.PlateNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/plate-numbers")
@CrossOrigin(origins = "*")
public class PlateNumberController {

    @Autowired
    private PlateNumberService plateNumberService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> registerPlateNumber(@Valid @RequestBody PlateNumberRequest request) {
        try {
            PlateNumberResponse response = plateNumberService.registerPlateNumber(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/owner/{ownerId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> getPlateNumbersByOwner(
            @PathVariable Long ownerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {

        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
            Page<PlateNumberResponse> plateNumbers = plateNumberService.getPlateNumbersByOwner(ownerId, pageable);
            return ResponseEntity.ok(plateNumbers);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/owner/{ownerId}/available")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> getAvailablePlateNumbersByOwner(@PathVariable Long ownerId) {
        try {
            List<PlateNumberResponse> availablePlateNumbers = plateNumberService.getAvailablePlateNumbersByOwner(ownerId);
            return ResponseEntity.ok(availablePlateNumbers);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> getPlateNumberById(@PathVariable Long id) {
        try {
            PlateNumberResponse response = plateNumberService.getPlateNumberById(id);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/value/{plateNumber}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> getPlateNumberByValue(@PathVariable String plateNumber) {
        try {
            PlateNumberResponse response = plateNumberService.getPlateNumberByPlateNumber(plateNumber);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}