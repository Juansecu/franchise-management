package com.juansecu.franchisemanagement.application.usecases;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import com.juansecu.franchisemanagement.domain.models.Branch;
import com.juansecu.franchisemanagement.domain.models.Franchise;
import com.juansecu.franchisemanagement.domain.repositories.IBranchRepository;
import com.juansecu.franchisemanagement.domain.repositories.IFranchiseRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateBranchUseCaseTest {
    @Mock
    private IBranchRepository branchRepository;

    @Mock
    private IFranchiseRepository franchiseRepository;

    @InjectMocks
    private CreateBranchUseCase createBranchUseCase;

    @Test
    void execute_shouldSaveAndReturnBranch() {
        String name = "New Branch";
        Long franchiseId = 1L;
        Franchise franchise = Franchise.builder().franchiseId(franchiseId).name("Franchise").build();
        Branch branch = Branch.builder().branchId(1L).name(name).franchiseId(franchiseId).build();

        when(
            franchiseRepository.findById(franchiseId)
        ).thenReturn(Mono.just(franchise));
        when(
            branchRepository.findByNameAndFranchiseId(name, franchiseId)
        ).thenReturn(Mono.empty());
        when(
            branchRepository.save(any(Branch.class))
        ).thenReturn(Mono.just(branch));

        createBranchUseCase.execute(name, franchiseId)
            .as(StepVerifier::create)
            .expectNextMatches(b -> b.getBranchId().equals(1L) && b.getName().equals(name))
            .verifyComplete();
    }

    @Test
    void execute_whenFranchiseNotFound_shouldReturnError() {
        when(franchiseRepository.findById(1L)).thenReturn(Mono.empty());

        createBranchUseCase.execute("Branch", 1L)
            .as(StepVerifier::create)
            .expectErrorMessage("Franchise not found with ID: 1")
            .verify();
    }
}
