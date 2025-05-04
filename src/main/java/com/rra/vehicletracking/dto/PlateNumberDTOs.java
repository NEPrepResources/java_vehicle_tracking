package com.rra.vehicletracking.dto;

import com.rra.vehicletracking.entity.PlateNumber;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class PlateNumberDTOs {

    public static class PlateNumberRequest {
        @NotBlank(message = "Plate number is required")
        @Pattern(regexp = "^[A-Z0-9]{2,3}\\s?[0-9]{3}[A-Z]$", message = "Plate number must be in the format RAA 123A")
        private String plateNumber;

        @NotNull(message = "Owner ID is required")
        private Long ownerId;

        private LocalDate issuedDate = LocalDate.now();

        public PlateNumberRequest() {
        }

        public PlateNumberRequest(String plateNumber, Long ownerId, LocalDate issuedDate) {
            this.plateNumber = plateNumber;
            this.ownerId = ownerId;
            this.issuedDate = issuedDate;
        }

        public @NotBlank(message = "Plate number is required") @Pattern(regexp = "^[A-Z0-9]{2,3}\\s?[0-9]{3}[A-Z]$", message = "Plate number must be in the format RAA 123A") String getPlateNumber() {
            return plateNumber;
        }

        public void setPlateNumber(@NotBlank(message = "Plate number is required") @Pattern(regexp = "^[A-Z0-9]{2,3}\\s?[0-9]{3}[A-Z]$", message = "Plate number must be in the format RAA 123A") String plateNumber) {
            this.plateNumber = plateNumber;
        }

        public @NotNull(message = "Owner ID is required") Long getOwnerId() {
            return ownerId;
        }

        public void setOwnerId(@NotNull(message = "Owner ID is required") Long ownerId) {
            this.ownerId = ownerId;
        }

        public LocalDate getIssuedDate() {
            return issuedDate;
        }

        public void setIssuedDate(LocalDate issuedDate) {
            this.issuedDate = issuedDate;
        }
    }

    public static class PlateNumberResponse {
        private Long id;
        private String plateNumber;
        private LocalDate issuedDate;
        private Long ownerId;
        private String ownerNames;
        private PlateNumber.PlateStatus status;

        public PlateNumberResponse() {
        }

        public PlateNumberResponse(Long id, String plateNumber, LocalDate issuedDate, Long ownerId, String ownerNames, PlateNumber.PlateStatus status) {
            this.id = id;
            this.plateNumber = plateNumber;
            this.issuedDate = issuedDate;
            this.ownerId = ownerId;
            this.ownerNames = ownerNames;
            this.status = status;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getPlateNumber() {
            return plateNumber;
        }

        public void setPlateNumber(String plateNumber) {
            this.plateNumber = plateNumber;
        }

        public LocalDate getIssuedDate() {
            return issuedDate;
        }

        public void setIssuedDate(LocalDate issuedDate) {
            this.issuedDate = issuedDate;
        }

        public Long getOwnerId() {
            return ownerId;
        }

        public void setOwnerId(Long ownerId) {
            this.ownerId = ownerId;
        }

        public String getOwnerNames() {
            return ownerNames;
        }

        public void setOwnerNames(String ownerNames) {
            this.ownerNames = ownerNames;
        }

        public PlateNumber.PlateStatus getStatus() {
            return status;
        }

        public void setStatus(PlateNumber.PlateStatus status) {
            this.status = status;
        }
    }

}