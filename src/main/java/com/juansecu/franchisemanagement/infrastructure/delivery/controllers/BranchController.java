package com.juansecu.franchisemanagement.infrastructure.delivery.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import com.juansecu.franchisemanagement.application.usecases.CreateBranchUseCase;
import com.juansecu.franchisemanagement.infrastructure.delivery.dtos.requests.CreateBranchRequest;
import com.juansecu.franchisemanagement.infrastructure.delivery.dtos.responses.BranchResponse;

@RestController
@RequestMapping("/api/branches")
@RequiredArgsConstructor
public class BranchController {
    private final CreateBranchUseCase createBranchUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<BranchResponse> createBranch(
        @Valid @RequestBody CreateBranchRequest request
    ) {
        return createBranchUseCase
            .execute(request.getName(), request.getFranchiseId())
            .map(branch ->
                BranchResponse.builder()
                    .branchId(branch.getBranchId())
                    .name(branch.getName())
                    .franchiseId(branch.getFranchiseId())
                    .build()
            );
    }
}
