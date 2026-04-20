package com.juansecu.franchisemanagement.infrastructure.delivery.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BranchResponse {
    private Long branchId;
    private String name;
    private Long franchiseId;
}
