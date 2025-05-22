package com.rra.vehicletracking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class VehicleOwnerDTOs {

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

        public @NotBlank(message = "Owner names are required") @Size(min = 3, max = 100, message = "Owner names must be between 3 and 100 characters") String getOwnerNames() {
            return ownerNames;
        }

        public void setOwnerNames(@NotBlank(message = "Owner names are required") @Size(min = 3, max = 100, message = "Owner names must be between 3 and 100 characters") String ownerNames) {
            this.ownerNames = ownerNames;
        }

        public @NotBlank(message = "National ID is required") @Size(min = 16, max = 16, message = "National ID must be 16 characters") String getNationalId() {
            return nationalId;
        }

        public void setNationalId(@NotBlank(message = "National ID is required") @Size(min = 16, max = 16, message = "National ID must be 16 characters") String nationalId) {
            this.nationalId = nationalId;
        }

        public @NotBlank(message = "Phone number is required") @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Phone number must be valid") String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(@NotBlank(message = "Phone number is required") @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Phone number must be valid") String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public @NotBlank(message = "Address is required") String getAddress() {
            return address;
        }

        public void setAddress(@NotBlank(message = "Address is required") String address) {
            this.address = address;
        }
    }

    public static class OwnerResponse {
        private Long id;
        private String ownerNames;
        private String nationalId;
        private String phoneNumber;
        private String address;

        public OwnerResponse() {
        }

        public OwnerResponse(Long id, String ownerNames, String nationalId, String phoneNumber, String address) {
            this.id = id;
            this.ownerNames = ownerNames;
            this.nationalId = nationalId;
            this.phoneNumber = phoneNumber;
            this.address = address;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getOwnerNames() {
            return ownerNames;
        }

        public void setOwnerNames(String ownerNames) {
            this.ownerNames = ownerNames;
        }

        public String getNationalId() {
            return nationalId;
        }

        public void setNationalId(String nationalId) {
            this.nationalId = nationalId;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }

    @NoArgsConstructor
    @AllArgsConstructor
    public static class OwnerSearchCriteria {
        private String nationalId;
        private String phoneNumber;
        private Integer page;
        private Integer size;

        public String getNationalId() {
            return nationalId;
        }

        public void setNationalId(String nationalId) {
            this.nationalId = nationalId;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
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