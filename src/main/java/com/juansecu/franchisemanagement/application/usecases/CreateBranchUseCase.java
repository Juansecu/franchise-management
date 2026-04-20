package com.juansecu.franchisemanagement.application.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import com.juansecu.franchisemanagement.application.exceptions.HttpException;
import com.juansecu.franchisemanagement.domain.models.Branch;
import com.juansecu.franchisemanagement.domain.repositories.IBranchRepository;
import com.juansecu.franchisemanagement.domain.repositories.IFranchiseRepository;

@Service
@RequiredArgsConstructor
public class CreateBranchUseCase {
    private final IBranchRepository branchRepository;
    private final IFranchiseRepository franchiseRepository;

    public Mono<Branch> execute(final String name, final Long franchiseId) {
        return franchiseRepository
            .findById(franchiseId)
            .switchIfEmpty(
                Mono.error(
                    HttpException.notFound("Franchise not found with ID: " + franchiseId)
                )
            )
            .flatMap(franchise ->
                branchRepository.findByNameAndFranchiseId(name, franchiseId)
            )
            .flatMap(existingBranch ->
                Mono.<Branch>error(
                    HttpException.conflict("Branch with name " + name + " already exists in this franchise")
                )
            )
            .switchIfEmpty(
                Mono.defer(() -> {
                    final Branch branch = Branch.builder()
                        .name(name)
                        .franchiseId(franchiseId)
                        .build();

                    return branchRepository.save(branch);
                })
            );
    }
}
