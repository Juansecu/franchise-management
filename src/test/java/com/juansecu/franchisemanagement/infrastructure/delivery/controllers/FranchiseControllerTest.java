package com.juansecu.franchisemanagement.infrastructure.delivery.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webflux.test.autoconfigure.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import com.juansecu.franchisemanagement.application.usecases.CreateFranchiseUseCase;
import com.juansecu.franchisemanagement.domain.models.Franchise;
import com.juansecu.franchisemanagement.infrastructure.delivery.dtos.requests.CreateFranchiseRequest;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@WebFluxTest(controllers = FranchiseController.class)
class FranchiseControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private CreateFranchiseUseCase createFranchiseUseCase;

    @Test
    void createFranchise_shouldReturnCreated() {
        CreateFranchiseRequest request = CreateFranchiseRequest.builder()
                .name("New Franchise")
                .build();

        final Franchise franchise = Franchise.builder()
                .franchiseId(1L)
                .name("New Franchise")
                .build();

        when(createFranchiseUseCase.execute(anyString()))
                .thenReturn(Mono.just(franchise));

        webTestClient.post()
                .uri("/api/franchises")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.franchiseId").isEqualTo(1)
                .jsonPath("$.name").isEqualTo("New Franchise");
    }
}
