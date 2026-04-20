package com.juansecu.franchisemanagement.infrastructure.delivery.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import com.juansecu.franchisemanagement.application.usecases.CreateFranchiseUseCase;
import com.juansecu.franchisemanagement.infrastructure.delivery.dtos.requests.CreateFranchiseRequest;
import com.juansecu.franchisemanagement.infrastructure.delivery.dtos.responses.FranchiseResponse;

@RestController
@RequestMapping("/api/franchises")
@RequiredArgsConstructor
public class FranchiseController {
    private final CreateFranchiseUseCase createFranchiseUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<FranchiseResponse> createFranchise(@Valid @RequestBody CreateFranchiseRequest request) {
        return createFranchiseUseCase
            .execute(request.getName())
            .map(franchise ->
                FranchiseResponse.builder()
                    .franchiseId(franchise.getFranchiseId())
                    .name(franchise.getName())
                    .build()
            );
    }
}
