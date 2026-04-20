package com.juansecu.franchisemanagement.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Franchise {
    private Long franchiseId;
    private String name;
}
