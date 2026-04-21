package com.juansecu.franchisemanagement.infrastructure.delivery.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class UpdateFranchiseNameRequest {
    @NotBlank(message = "New franchise name is mandatory")
    @Size(min = 3, max = 100, message = "Franchise name must be between 3 and 100 characters")
    private String name;
}
