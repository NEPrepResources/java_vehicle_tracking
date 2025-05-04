package com.rra.vehicletracking.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

public class VehicleDTOs {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VehicleRegistrationRequest {
        @NotBlank(message = "Chassis number is required")
        @Pattern(regexp = "^[A-Z0-9]{6,17}$", message = "Chassis number must be 6-17 alphanumeric characters")
        private String chassisNumber;

        @NotBlank(message = "Manufacture company is required")
        private String manufactureCompany;

        @NotNull(message = "Manufacture year is required")
        @Min(value = 1900, message = "Manufacture year must be after 1900")
        @Max(value = 2100, message = "Manufacture year must be before 2100")
        private Integer manufactureYear;

        @NotNull(message = "Price is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Price must be positive")
        private BigDecimal price;

        @NotBlank(message = "Model name is required")
        private String modelName;

        @NotNull(message = "Owner ID is required")
        private Long ownerId;

        @NotBlank(message = "Plate number is required")
        private String plateNumber;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VehicleResponse {
        private Long id;
        private String chassisNumber;
        private String manufactureCompany;
        private Integer manufactureYear;
        private BigDecimal price;
        private String modelName;
        private String plateNumber;
        private String ownerNames;
        private String ownerNationalId;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VehicleTransferRequest {
        @NotBlank(message = "Chassis number or plate number is required")
        private String vehicleIdentifier; // Can be chassis number or plate number

        @NotNull(message = "New owner ID is required")
        private Long newOwnerId;

        @NotBlank(message = "New plate number is required")
        private String newPlateNumber;

        @NotNull(message = "Purchase price is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Purchase price must be positive")
        private BigDecimal purchasePrice;

        private String comments;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VehicleSearchRequest {
        private String chassisNumber;
        private String plateNumber;
        private String ownerNationalId;
        private Integer page;
        private Integer size;
    }
}