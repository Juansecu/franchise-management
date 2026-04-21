package com.juansecu.franchisemanagement.application.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import com.juansecu.franchisemanagement.application.exceptions.HttpException;
import com.juansecu.franchisemanagement.domain.models.Branch;
import com.juansecu.franchisemanagement.domain.repositories.IBranchRepository;

@Service
@RequiredArgsConstructor
public class UpdateBranchNameUseCase {
    private final IBranchRepository branchRepository;

    public Mono<Branch> execute(final Long branchId, final String newName) {
        return branchRepository
            .findById(branchId)
            .switchIfEmpty(
                Mono.error(
                    HttpException.notFound("Branch not found with ID: " + branchId)
                )
            )
            .flatMap(existingBranch ->
                branchRepository
                    .findByNameAndFranchiseId(newName, existingBranch.getFranchiseId())
                    .flatMap(branchWithSameName -> {
                        if (!branchWithSameName.getBranchId().equals(branchId))
                            return Mono.error(
                                HttpException.conflict("Branch with name " + newName + " already exists in this franchise")
                            );

                        return Mono.just(existingBranch);
                    })
                    .switchIfEmpty(Mono.just(existingBranch))
                    .flatMap(branchToUpdate -> {
                        branchToUpdate.setName(newName);
                        return branchRepository.save(branchToUpdate);
                    })
            );
    }
}
