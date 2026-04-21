package com.juansecu.franchisemanagement.infrastructure.delivery.dtos.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class UpdateStockRequest {
    @NotNull(message = "New stock is mandatory")
    @Min(value = 0, message = "Stock cannot be negative")
    private Integer stock;
}
