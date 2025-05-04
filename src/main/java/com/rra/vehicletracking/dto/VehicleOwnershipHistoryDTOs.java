package com.rra.vehicletracking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class VehicleOwnershipHistoryDTOs {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
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
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OwnershipHistorySearchRequest {
        private String chassisNumber;
        private String plateNumber;
        private Integer page;
        private Integer size;
    }
}