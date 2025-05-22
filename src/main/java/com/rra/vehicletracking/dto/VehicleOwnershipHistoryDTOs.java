package com.rra.vehicletracking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class VehicleOwnershipHistoryDTOs {

    public static class OwnershipHistoryResponse {
        private Long id;
        private Long vehicleId;
        private String chassisNumber;
        private String ownerNames;
        private String ownerNationalId;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private BigDecimal purchasePrice;
        private String plateNumber;
        private String comments;

        public OwnershipHistoryResponse() {
        }

        public OwnershipHistoryResponse(Long id, Long vehicleId, String chassisNumber, String ownerNames, String ownerNationalId, LocalDateTime startDate, LocalDateTime endDate, BigDecimal purchasePrice, String plateNumber, String comments) {
            this.id = id;
            this.vehicleId = vehicleId;
            this.chassisNumber = chassisNumber;
            this.ownerNames = ownerNames;
            this.ownerNationalId = ownerNationalId;
            this.startDate = startDate;
            this.endDate = endDate;
            this.purchasePrice = purchasePrice;
            this.plateNumber = plateNumber;
            this.comments = comments;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getVehicleId() {
            return vehicleId;
        }

        public void setVehicleId(Long vehicleId) {
            this.vehicleId = vehicleId;
        }

        public String getChassisNumber() {
            return chassisNumber;
        }

        public void setChassisNumber(String chassisNumber) {
            this.chassisNumber = chassisNumber;
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

        public LocalDateTime getStartDate() {
            return startDate;
        }

        public void setStartDate(LocalDateTime startDate) {
            this.startDate = startDate;
        }

        public LocalDateTime getEndDate() {
            return endDate;
        }

        public void setEndDate(LocalDateTime endDate) {
            this.endDate = endDate;
        }

        public BigDecimal getPurchasePrice() {
            return purchasePrice;
        }

        public void setPurchasePrice(BigDecimal purchasePrice) {
            this.purchasePrice = purchasePrice;
        }

        public String getPlateNumber() {
            return plateNumber;
        }

        public void setPlateNumber(String plateNumber) {
            this.plateNumber = plateNumber;
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
    public static class OwnershipHistorySearchRequest {
        private String chassisNumber;
        private String plateNumber;
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