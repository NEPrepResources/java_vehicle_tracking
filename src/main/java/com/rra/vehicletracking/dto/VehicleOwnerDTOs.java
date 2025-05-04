package com.rra.vehicletracking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class VehicleOwnerDTOs {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OwnerRequest {
        @NotBlank(message = "Owner names are required")
        @Size(min = 3, max = 100, message = "Owner names must be between 3 and 100 characters")
        private String ownerNames;

        @NotBlank(message = "National ID is required")
        @Size(min = 16, max = 16, message = "National ID must be 16 characters")
        private String nationalId;

        @NotBlank(message = "Phone number is required")
        @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Phone number must be valid")
        private String phoneNumber;

        @NotBlank(message = "Address is required")
        private String address;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OwnerResponse {
        private Long id;
        private String ownerNames;
        private String nationalId;
        private String phoneNumber;
        private String address;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OwnerSearchCriteria {
        private String nationalId;
        private String phoneNumber;
        private Integer page;
        private Integer size;
    }
}