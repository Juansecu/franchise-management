package com.juansecu.franchisemanagement.infrastructure.delivery.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class ProductResponse {
    private Long productId;
    private String name;
    private Integer stock;
    private Long branchId;
}
