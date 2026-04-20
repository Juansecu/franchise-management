package com.juansecu.franchisemanagement.application.usecases;

import com.juansecu.franchisemanagement.domain.models.Franchise;
import com.juansecu.franchisemanagement.domain.repositories.IFranchiseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateFranchiseUseCaseTest {

    @Mock
    private IFranchiseRepository franchiseRepository;

    @InjectMocks
    private CreateFranchiseUseCase createFranchiseUseCase;

    @Test
    void execute_shouldSaveAndReturnFranchise() {
        String name = "New Franchise";
        Franchise franchise = Franchise.builder().franchiseId(1L).name(name).build();

        when(
            franchiseRepository.findByNameIgnoreCase(name)
        ).thenReturn(Mono.empty());
        when(
            franchiseRepository.save(any(Franchise.class))
        ).thenReturn(Mono.just(franchise));

        createFranchiseUseCase.execute(name)
                .as(StepVerifier::create)
                .expectNextMatches(f ->
                    f.getFranchiseId().equals(1L) &&
                    f.getName().equals(name)
                )
                .verifyComplete();
    }

    @Test
    void execute_whenNameExists_shouldReturnError() {
        String name = "Existing Franchise";
        Franchise existingFranchise = Franchise.builder().franchiseId(1L).name(name).build();

        when(franchiseRepository.findByNameIgnoreCase(name)).thenReturn(Mono.just(existingFranchise));

        createFranchiseUseCase.execute(name)
                .as(StepVerifier::create)
                .expectErrorMessage("Franchise with name Existing Franchise already exists")
                .verify();
    }
}
