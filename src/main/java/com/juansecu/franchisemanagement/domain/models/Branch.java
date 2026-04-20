package com.juansecu.franchisemanagement.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Branch {
    private Long branchId;
    private String name;
    private Long franchiseId;
}
