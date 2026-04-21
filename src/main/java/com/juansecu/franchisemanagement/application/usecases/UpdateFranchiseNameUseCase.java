package com.juansecu.franchisemanagement.application.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import com.juansecu.franchisemanagement.application.exceptions.HttpException;
import com.juansecu.franchisemanagement.domain.models.Franchise;
import com.juansecu.franchisemanagement.domain.repositories.IFranchiseRepository;

@Service
@RequiredArgsConstructor
public class UpdateFranchiseNameUseCase {
    private final IFranchiseRepository franchiseRepository;

    public Mono<Franchise> execute(Long franchiseId, String newName) {
        return franchiseRepository
            .findById(franchiseId)
            .switchIfEmpty(
                Mono.error(
                    HttpException.notFound("Franchise not found with ID: " + franchiseId)
                )
            )
            .flatMap(existingFranchise ->
                franchiseRepository
                    .findByNameIgnoreCase(newName)
                    .flatMap(franchiseWithSameName -> {
                        if (!franchiseWithSameName.getFranchiseId().equals(franchiseId))
                            return Mono.error(
                                HttpException.conflict("Franchise with name " + newName + " already exists")
                            );

                        return Mono.just(existingFranchise);
                    })
                    .switchIfEmpty(Mono.just(existingFranchise))
                    .flatMap(franchiseToUpdate -> {
                        franchiseToUpdate.setName(newName);
                        return franchiseRepository.save(franchiseToUpdate);
                    })
            );
    }
}
