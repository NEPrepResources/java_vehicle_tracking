package com.rra.vehicletracking.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

public class VehicleDTOs {
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

        public @NotBlank(message = "Chassis number is required") @Pattern(regexp = "^[A-Z0-9]{6,17}$", message = "Chassis number must be 6-17 alphanumeric characters") String getChassisNumber() {
            return chassisNumber;
        }

        public void setChassisNumber(@NotBlank(message = "Chassis number is required") @Pattern(regexp = "^[A-Z0-9]{6,17}$", message = "Chassis number must be 6-17 alphanumeric characters") String chassisNumber) {
            this.chassisNumber = chassisNumber;
        }

        public @NotBlank(message = "Manufacture company is required") String getManufactureCompany() {
            return manufactureCompany;
        }

        public void setManufactureCompany(@NotBlank(message = "Manufacture company is required") String manufactureCompany) {
            this.manufactureCompany = manufactureCompany;
        }

        public @NotNull(message = "Manufacture year is required") @Min(value = 1900, message = "Manufacture year must be after 1900") @Max(value = 2100, message = "Manufacture year must be before 2100") Integer getManufactureYear() {
            return manufactureYear;
        }

        public void setManufactureYear(@NotNull(message = "Manufacture year is required") @Min(value = 1900, message = "Manufacture year must be after 1900") @Max(value = 2100, message = "Manufacture year must be before 2100") Integer manufactureYear) {
            this.manufactureYear = manufactureYear;
        }

        public @NotNull(message = "Price is required") @DecimalMin(value = "0.0", inclusive = false, message = "Price must be positive") BigDecimal getPrice() {
            return price;
        }

        public void setPrice(@NotNull(message = "Price is required") @DecimalMin(value = "0.0", inclusive = false, message = "Price must be positive") BigDecimal price) {
            this.price = price;
        }

        public @NotBlank(message = "Model name is required") String getModelName() {
            return modelName;
        }

        public void setModelName(@NotBlank(message = "Model name is required") String modelName) {
            this.modelName = modelName;
        }

        public @NotNull(message = "Owner ID is required") Long getOwnerId() {
            return ownerId;
        }

        public void setOwnerId(@NotNull(message = "Owner ID is required") Long ownerId) {
            this.ownerId = ownerId;
        }

        public @NotBlank(message = "Plate number is required") String getPlateNumber() {
            return plateNumber;
        }

        public void setPlateNumber(@NotBlank(message = "Plate number is required") String plateNumber) {
            this.plateNumber = plateNumber;
        }
    }

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

        public VehicleResponse() {
        }

        public VehicleResponse(Long id, String chassisNumber, String manufactureCompany, Integer manufactureYear, BigDecimal price, String modelName, String plateNumber, String ownerNames, String ownerNationalId) {
            this.id = id;
            this.chassisNumber = chassisNumber;
            this.manufactureCompany = manufactureCompany;
            this.manufactureYear = manufactureYear;
            this.price = price;
            this.modelName = modelName;
            this.plateNumber = plateNumber;
            this.ownerNames = ownerNames;
            this.ownerNationalId = ownerNationalId;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getChassisNumber() {
            return chassisNumber;
        }

        public void setChassisNumber(String chassisNumber) {
            this.chassisNumber = chassisNumber;
        }

        public String getManufactureCompany() {
            return manufactureCompany;
        }

        public void setManufactureCompany(String manufactureCompany) {
            this.manufactureCompany = manufactureCompany;
        }

        public Integer getManufactureYear() {
            return manufactureYear;
        }

        public void setManufactureYear(Integer manufactureYear) {
            this.manufactureYear = manufactureYear;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        public String getModelName() {
            return modelName;
        }

        public void setModelName(String modelName) {
            this.modelName = modelName;
        }

        public String getPlateNumber() {
            return plateNumber;
        }

        public void setPlateNumber(String plateNumber) {
            this.plateNumber = plateNumber;
        }

        public String getOwnerNames() {
            return ownerNames;
        }

        public void setOwnerNames(String ownerNames) {
            this.ownerNames = ownerNames;
        }

        public String getOwnerNationalId() {
            return ownerNationalId;
        }

        public void setOwnerNationalId(String ownerNationalId) {
            this.ownerNationalId = ownerNationalId;
        }
    }

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

        public @NotBlank(message = "Chassis number or plate number is required") String getVehicleIdentifier() {
            return vehicleIdentifier;
        }

        public void setVehicleIdentifier(@NotBlank(message = "Chassis number or plate number is required") String vehicleIdentifier) {
            this.vehicleIdentifier = vehicleIdentifier;
        }

        public @NotNull(message = "New owner ID is required") Long getNewOwnerId() {
            return newOwnerId;
        }

        public void setNewOwnerId(@NotNull(message = "New owner ID is required") Long newOwnerId) {
            this.newOwnerId = newOwnerId;
        }

        public @NotBlank(message = "New plate number is required") String getNewPlateNumber() {
            return newPlateNumber;
        }

        public void setNewPlateNumber(@NotBlank(message = "New plate number is required") String newPlateNumber) {
            this.newPlateNumber = newPlateNumber;
        }

        public @NotNull(message = "Purchase price is required") @DecimalMin(value = "0.0", inclusive = false, message = "Purchase price must be positive") BigDecimal getPurchasePrice() {
            return purchasePrice;
        }

        public void setPurchasePrice(@NotNull(message = "Purchase price is required") @DecimalMin(value = "0.0", inclusive = false, message = "Purchase price must be positive") BigDecimal purchasePrice) {
            this.purchasePrice = purchasePrice;
        }

        public String getComments() {
            return comments;
        }

        public void setComments(String comments) {
            this.comments = comments;
        }
    }

    @NoArgsConstructor
    @AllArgsConstructor
    public static class VehicleSearchRequest {
        private String chassisNumber;
        private String plateNumber;
        private String ownerNationalId;
        private Integer page;
        private Integer size;

        public String getChassisNumber() {
            return chassisNumber;
        }

        public void setChassisNumber(String chassisNumber) {
            this.chassisNumber = chassisNumber;
        }

        public String getPlateNumber() {
            return plateNumber;
        }

        public void setPlateNumber(String plateNumber) {
            this.plateNumber = plateNumber;
        }

        public String getOwnerNationalId() {
            return ownerNationalId;
        }

        public void setOwnerNationalId(String ownerNationalId) {
            this.ownerNationalId = ownerNationalId;
        }

        public Integer getPage() {
            return page;
        }

        public void setPage(Integer page) {
            this.page = page;
        }

        public Integer getSize() {
            return size;
        }

        public void setSize(Integer size) {
            this.size = size;
        }
    }
}