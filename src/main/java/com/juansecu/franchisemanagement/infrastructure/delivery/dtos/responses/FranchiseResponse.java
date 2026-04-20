package com.juansecu.franchisemanagement.infrastructure.delivery.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class FranchiseResponse {
    private Long franchiseId;
    private String name;
}
