package com.juansecu.franchisemanagement.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class Product {
    private Long productId;
    private String name;
    private Integer stock;
    private Long branchId;
}
