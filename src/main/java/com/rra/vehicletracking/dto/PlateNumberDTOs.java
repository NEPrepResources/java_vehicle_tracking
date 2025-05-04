package com.rra.vehicletracking.dto;

import com.rra.vehicletracking.entity.PlateNumber;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class PlateNumberDTOs {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlateNumberRequest {
        @NotBlank(message = "Plate number is required")
        @Pattern(regexp = "^[A-Z0-9]{2,3}\\s?[0-9]{3}[A-Z]$", message = "Plate number must be in the format RAA 123A")
        private String plateNumber;

        @NotNull(message = "Owner ID is required")
        private Long ownerId;

        private LocalDate issuedDate = LocalDate.now();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlateNumberResponse {
        private Long id;
        private String plateNumber;
        private LocalDate issuedDate;
        private Long ownerId;
        private String ownerNames;
        private PlateNumber.PlateStatus status;
    }

}