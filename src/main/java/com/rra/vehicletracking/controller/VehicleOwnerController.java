package com.rra.vehicletracking.controller;

import com.rra.vehicletracking.dto.VehicleOwnerDTOs.OwnerRequest;
import com.rra.vehicletracking.dto.VehicleOwnerDTOs.OwnerResponse;
import com.rra.vehicletracking.service.VehicleOwnerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@RestController
@RequestMapping("/api/owners")
@CrossOrigin(origins = "*")
@Tag(name = "Vehicle Owners", description = "Endpoints for managing vehicle owners")
public class VehicleOwnerController {

    @Autowired
    private VehicleOwnerService vehicleOwnerService;

    @PostMapping
    @Operation(
            summary = "Create a new vehicle owner",
            description = "Create a new vehicle owner (Admin only)",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Owner created", content = @Content(schema = @Schema(implementation = OwnerResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request - Invalid input", content = @Content(schema = @Schema(implementation = String.class)))
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> createVehicleOwner(@Valid @RequestBody OwnerRequest request) {
        try {
            OwnerResponse response = vehicleOwnerService.createVehicleOwner(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update an existing vehicle owner",
            description = "Update the details of an existing vehicle owner (Admin only)",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Owner updated", content = @Content(schema = @Schema(implementation = OwnerResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request - Invalid input", content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "404", description = "Owner not found", content = @Content(schema = @Schema(implementation = String.class)))
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> updateVehicleOwner(@PathVariable Long id, @Valid @RequestBody OwnerRequest request) {
        try {
            OwnerResponse response = vehicleOwnerService.updateVehicleOwner(id, request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a vehicle owner",
            description = "Delete a vehicle owner by their ID (Admin only)",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Owner deleted successfully", content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "404", description = "Owner not found", content = @Content(schema = @Schema(implementation = String.class)))
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> deleteVehicleOwner(@PathVariable Long id) {
        try {
            vehicleOwnerService.deleteVehicleOwner(id);
            return ResponseEntity.ok("Vehicle owner deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get vehicle owner by ID",
            description = "Retrieve a vehicle owner by their ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful retrieval", content = @Content(schema = @Schema(implementation = OwnerResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Owner not found", content = @Content(schema = @Schema(implementation = String.class)))
            }
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getVehicleOwnerById(@PathVariable Long id) {
        try {
            OwnerResponse response = vehicleOwnerService.getVehicleOwnerById(id);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/national-id/{nationalId}")
    @Operation(
            summary = "Get vehicle owner by national ID",
            description = "Retrieve a vehicle owner by their national ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful retrieval", content = @Content(schema = @Schema(implementation = OwnerResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Owner not found", content = @Content(schema = @Schema(implementation = String.class)))
            }
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getVehicleOwnerByNationalId(@PathVariable String nationalId) {
        try {
            OwnerResponse response = vehicleOwnerService.getVehicleOwnerByNationalId(nationalId);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping
    @Operation(
            summary = "Search vehicle owners",
            description = "Search vehicle owners by national ID or phone number with pagination",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful retrieval", content = @Content(schema = @Schema(implementation = Page.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = String.class)))
            }
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> searchVehicleOwners(
            @RequestParam(required = false) String nationalId,
            @RequestParam(required = false) String phoneNumber,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
            Page<OwnerResponse> owners = vehicleOwnerService.searchVehicleOwners(nationalId, phoneNumber, pageable);
            return ResponseEntity.ok(owners);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}