package com.juansecu.franchisemanagement.application.usecases;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import com.juansecu.franchisemanagement.domain.models.Branch;
import com.juansecu.franchisemanagement.domain.repositories.IBranchRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateBranchNameUseCaseTest {
    @Mock
    private IBranchRepository branchRepository;

    @InjectMocks
    private UpdateBranchNameUseCase updateBranchNameUseCase;

    @Test
    void execute_shouldUpdateBranchNameWhenValid() {
        Long branchId = 1L;
        Long franchiseId = 10L;
        String newName = "New Branch Name";
        Branch existingBranch = Branch.builder()
            .branchId(branchId)
            .franchiseId(franchiseId)
            .name("Old Name")
            .build();
        Branch updatedBranch = Branch.builder()
            .branchId(branchId)
            .franchiseId(franchiseId)
            .name(newName)
            .build();

        when(
            branchRepository.findById(branchId)
        ).thenReturn(Mono.just(existingBranch));
        when(
            branchRepository.findByNameAndFranchiseId(newName, franchiseId)
        ).thenReturn(Mono.empty());
        when(
            branchRepository.save(any(Branch.class))
        ).thenReturn(Mono.just(updatedBranch));

        updateBranchNameUseCase
            .execute(branchId, newName)
            .as(StepVerifier::create)
            .expectNextMatches(b -> b.getName().equals(newName))
            .verifyComplete();

        verify(branchRepository, times(1)).findById(branchId);
        verify(branchRepository, times(1)).save(any(Branch.class));
    }

    @Test
    void execute_shouldReturnErrorWhenNameExistsInSameFranchise() {
        Long branchId = 1L;
        Long franchiseId = 10L;
        String newName = "Existing Name";
        Branch existingBranch = Branch.builder()
            .branchId(branchId)
            .franchiseId(franchiseId)
            .name("Old Name")
            .build();
        Branch otherBranch = Branch.builder()
            .branchId(2L)
            .franchiseId(franchiseId)
            .name(newName)
            .build();

        when(
            branchRepository.findById(branchId)
        ).thenReturn(Mono.just(existingBranch));
        when(
            branchRepository.findByNameAndFranchiseId(newName, franchiseId)
        ).thenReturn(Mono.just(otherBranch));

        updateBranchNameUseCase
            .execute(branchId, newName)
            .as(StepVerifier::create)
            .expectErrorMessage("Branch with name " + newName + " already exists in this franchise")
            .verify();

        verify(branchRepository, never()).save(any(Branch.class));
    }
}
