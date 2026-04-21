package com.juansecu.franchisemanagement.application.usecases;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import com.juansecu.franchisemanagement.domain.models.Franchise;
import com.juansecu.franchisemanagement.domain.repositories.IFranchiseRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateFranchiseNameUseCaseTest {
    @Mock
    private IFranchiseRepository franchiseRepository;

    @InjectMocks
    private UpdateFranchiseNameUseCase updateFranchiseNameUseCase;

    @Test
    void execute_shouldUpdateFranchiseNameWhenValid() {
        Long franchiseId = 1L;
        String oldName = "Old Franchise";
        String newName = "New Franchise";
        Franchise existingFranchise = Franchise.builder()
            .franchiseId(franchiseId)
            .name(oldName)
            .build();
        Franchise updatedFranchise = Franchise.builder()
            .franchiseId(franchiseId)
            .name(newName)
            .build();

        when(
            franchiseRepository.findById(franchiseId)
        ).thenReturn(Mono.just(existingFranchise));
        when(
            franchiseRepository.findByNameIgnoreCase(newName)
        ).thenReturn(Mono.empty());
        when(
            franchiseRepository.save(any(Franchise.class))
        ).thenReturn(Mono.just(updatedFranchise));

        updateFranchiseNameUseCase
            .execute(franchiseId, newName)
            .as(StepVerifier::create)
            .expectNextMatches(f -> f.getName().equals(newName))
            .verifyComplete();

        verify(franchiseRepository, times(1)).findById(franchiseId);
        verify(franchiseRepository, times(1)).findByNameIgnoreCase(newName);
        verify(franchiseRepository, times(1)).save(any(Franchise.class));
    }

    @Test
    void execute_shouldReturnErrorWhenFranchiseNotFound() {
        Long franchiseId = 1L;
        String newName = "New Franchise";

        when(
            franchiseRepository.findById(franchiseId)
        ).thenReturn(Mono.empty());

        updateFranchiseNameUseCase
            .execute(franchiseId, newName)
            .as(StepVerifier::create)
            .expectErrorMessage("Franchise not found with ID: " + franchiseId)
            .verify();

        verify(franchiseRepository, times(1)).findById(franchiseId);
        verify(franchiseRepository, never()).findByNameIgnoreCase(anyString());
        verify(franchiseRepository, never()).save(any(Franchise.class));
    }

    @Test
    void execute_shouldReturnErrorWhenNewNameAlreadyExists() {
        Long franchiseId = 1L;
        String oldName = "Old Franchise";
        String newName = "Existing Franchise";
        Franchise existingFranchise = Franchise.builder()
            .franchiseId(franchiseId)
            .name(oldName)
            .build();
        Franchise conflictingFranchise = Franchise.builder()
            .franchiseId(2L)
            .name(newName)
            .build();

        when(
            franchiseRepository.findById(franchiseId)
        ).thenReturn(Mono.just(existingFranchise));
        when(
            franchiseRepository.findByNameIgnoreCase(newName)
        ).thenReturn(Mono.just(conflictingFranchise));

        updateFranchiseNameUseCase
            .execute(franchiseId, newName)
            .as(StepVerifier::create)
            .expectErrorMessage("Franchise with name " + newName + " already exists")
            .verify();

        verify(franchiseRepository, times(1)).findById(franchiseId);
        verify(franchiseRepository, times(1)).findByNameIgnoreCase(newName);
        verify(franchiseRepository, never()).save(any(Franchise.class));
    }

    @Test
    void execute_shouldAllowSameNameForSameFranchise() {
        Long franchiseId = 1L;
        String newName = "Same Franchise Name";
        Franchise existingFranchise = Franchise.builder()
            .franchiseId(franchiseId)
            .name(newName)
            .build();
        Franchise updatedFranchise = Franchise.builder()
            .franchiseId(franchiseId)
            .name(newName)
            .build();

        when(
            franchiseRepository.findById(franchiseId)
        ).thenReturn(Mono.just(existingFranchise));
        when(
            franchiseRepository.findByNameIgnoreCase(newName)
        ).thenReturn(Mono.just(existingFranchise));
        when(
            franchiseRepository.save(any(Franchise.class))
        ).thenReturn(Mono.just(updatedFranchise));

        updateFranchiseNameUseCase
            .execute(franchiseId, newName)
            .as(StepVerifier::create)
            .expectNextMatches(f -> f.getName().equals(newName))
            .verifyComplete();

        verify(franchiseRepository, times(1)).findById(franchiseId);
        verify(franchiseRepository, times(1)).findByNameIgnoreCase(newName);
        verify(franchiseRepository, times(1)).save(any(Franchise.class));
    }
}
